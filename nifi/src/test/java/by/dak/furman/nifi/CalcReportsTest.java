package by.dak.furman.nifi;

import com.thoughtworks.xstream.InitializationException;
import org.apache.commons.io.FileUtils;
import org.apache.nifi.flowfile.attributes.CoreAttributes;
import org.apache.nifi.util.MockFlowFile;
import org.apache.nifi.util.TestRunner;
import org.apache.nifi.util.TestRunners;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CalcReportsTest {
    @Test
//	@Ignore
    public void test() throws InitializationException, IOException {
        final TestRunner runner = TestRunners.newTestRunner(CalcReports.class);
        runner.setIncomingConnection(true);
        runner.enqueue("");
        runner.setProperty(CalcReports.ORDER_ID, "10000005017");
        runner.setProperty(CalcReports.PROFILE, "prod");
        runner.run();
        List<MockFlowFile> files = runner.getFlowFilesForRelationship(CalcReports.REL_SUCCESS);
        MockFlowFile file = files.get(0);
        byte[] html = runner.getContentAsByteArray(file);
        FileUtils.writeByteArrayToFile(new File(String.format("build/%s",
                file.getAttribute(CoreAttributes.FILENAME.key()))), html);
        System.out.println(files);
    }
}
