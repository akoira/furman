package by.dak.persistence;

import by.dak.cutting.SpringConfiguration;
import org.apache.commons.io.IOUtils;
import workbench.db.WbConnection;
import workbench.sql.wbcommands.WbImport;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * User: akoyro
 * Date: 01.10.2010
 * Time: 20:44:55
 */
public class Importer extends AExecuter {

	public void execute() throws Exception {

		try {
			List<File> files = unzip();
			setConstraintsCheck(false);
			WbConnection wbConnection = new WbConnection(null, getConnection(), null);
			WbImport wbImport = new WbImport();
			wbImport.setConnection(wbConnection);
			for (String table : getTables()) {
				File file = getFileBy(files, table);
				wbImport.execute("-type=xml -file=" + file.getAbsolutePath() + " -mode='UPDATE,INSERT'" +
						" -table=" + table);
			}
		} finally {
			closeConnection();
		}
	}

	protected List<File> unzip() throws IOException {
		ArrayList<File> files = new ArrayList<File>();
		BufferedOutputStream dest;
		FileInputStream fis = new FileInputStream(getZipFile());
		ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
		ZipEntry entry;
		while ((entry = zis.getNextEntry()) != null) {
			Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Extracting: {0}", entry);
			int count;
			byte data[] = new byte[BUFFER_SIZE];
			File file = new File(getTargetDir(), entry.getName());
			files.add(file);
			FileOutputStream fos = new FileOutputStream(file);
			dest = new BufferedOutputStream(fos, BUFFER_SIZE);
			while ((count = zis.read(data, 0, BUFFER_SIZE)) != -1) {
				dest.write(data, 0, count);
			}
			IOUtils.closeQuietly(dest);
		}
		IOUtils.closeQuietly(zis);
		return files;
	}

	public static void main(String[] args) throws Exception {
		Importer importer = new Importer();
		SpringConfiguration springConfiguration = new SpringConfiguration();

		importer.setConnection(FacadeContext.getJDBCConnection());
		importer.setZipFile(new File("D:\\tmp\\test.zip"));
		importer.execute();
	}

	protected File getFileBy(List<File> files, String table) {
		for (File file : files) {
			if (file.getName().toLowerCase().startsWith(table.toLowerCase())) {
				return file;
			}
		}
		throw new IllegalArgumentException();
	}

}
