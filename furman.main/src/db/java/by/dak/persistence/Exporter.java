package by.dak.persistence;

import by.dak.cutting.SpringConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import workbench.db.WbConnection;
import workbench.sql.wbcommands.WbExport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * User: akoyro
 * Date: 01.10.2010
 * Time: 23:54:55
 */
public class Exporter extends AExecuter {
	public void execute() throws Exception {
		List<File> list = new ArrayList<File>();

		try {
			WbConnection wbConnection = new WbConnection(null, getConnection(), null);
			WbExport wbExport = new WbExport();
			wbExport.setConnection(wbConnection);
			for (String table : getTables()) {
				File file = new File(getTargetDir(), table + ".xml");
				list.add(file);
				Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Export db \"{0}\" to \"{1}\".", new Object[]{table, file.getAbsolutePath()});
				setShowDdInfo(false);
				wbExport.execute("-type=xml -file=" + file.getAbsolutePath() + " -sourceTable=" + table);
			}

		} finally {
			closeConnection();
			zip(list);
			deleteFiles(list);
			deleteTargetDir();
		}
	}

	private void zip(List<File> files) throws IOException {
		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead;
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(getZipFile()));
		for (File file : files) {
			ZipEntry entry = new ZipEntry(file.getName());
			FileInputStream in = new FileInputStream(file);
			out.putNextEntry(entry);
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
			IOUtils.closeQuietly(in);
		}
		IOUtils.closeQuietly(out);
	}


	public static void main(String[] args) throws Exception {
		SpringConfiguration springConfiguration = new SpringConfiguration();

		String[] tables = StringUtils.split("PRICE,FURNITURE_CODE,FURNITURE_TYPE", ',');
//        File targetDir = new File("D:\\tmp");
		File exportZip = new File("D:\\tmp\\test.zip");
		Exporter utils = new Exporter();
		utils.setConnection(FacadeContext.getJDBCConnection());
		utils.setTables(tables);
		utils.setZipFile(exportZip);
		utils.execute();
	}

}
