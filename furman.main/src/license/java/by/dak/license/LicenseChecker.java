package by.dak.license;

import com.verhas.licensor.HardwareBinder;
import com.verhas.licensor.License;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * User: akoyro
 * Date: 06.10.2010
 * Time: 10:58:35
 */
public class LicenseChecker
{
    public static final SimpleDateFormat LICENSE_DATA_FORMAT = new SimpleDateFormat("yyyy.MM.dd");

    public static final String PROPERTY_dialer = "dialer";
    public static final String PROPERTY_valid = "valid";
    public static final String PROPERTY_hwkey = "hwkey";
    public static final String PROPERTY_issue = "issue";

    public static final String LICENSE_FILE_NAME = "license.crypt";

    //подпись для public ключа можно получить license3j decode --license-file=lic.txt --keyring-file=pubring.gpg
    byte[] digest = new byte[]{
            (byte) 0xA8,
            (byte) 0xBD, (byte) 0x33, (byte) 0xCE, (byte) 0xA3, (byte) 0xC1, (byte) 0xE7, (byte) 0x5F, (byte) 0x64,
            (byte) 0x2B, (byte) 0x8B, (byte) 0x1E, (byte) 0xCA, (byte) 0x76, (byte) 0x71, (byte) 0x20, (byte) 0xEA,
            (byte) 0x27, (byte) 0x67, (byte) 0x51, (byte) 0x6C, (byte) 0xA0, (byte) 0x7A, (byte) 0x83, (byte) 0xD8,
            (byte) 0xD5, (byte) 0x0F, (byte) 0xAE, (byte) 0x3A, (byte) 0x77, (byte) 0x64, (byte) 0x5A,
    };
    private License license;

    public void check()
    {
        try
        {
            HardwareBinder hb = new HardwareBinder();
            hb.setUseHwAddress();
            String todayHwkey = hb.getMachineIdString();

            Date today = LICENSE_DATA_FORMAT.parse(LICENSE_DATA_FORMAT.format(Calendar.getInstance().getTime()));

            String dialerS = getLicense().getFeature(PROPERTY_dialer);
            String hwkeyS = getLicense().getFeature(PROPERTY_hwkey);
            String validS = getLicense().getFeature(PROPERTY_valid);
            String issueS = getLicense().getFeature(PROPERTY_issue);


            Date issue = LICENSE_DATA_FORMAT.parse(issueS);
            Date valid = LICENSE_DATA_FORMAT.parse(validS);

            if (today.compareTo(issue) < 0)
            {
                throw new IllegalArgumentException("Issue date is too late, probably tampered system time");
            }
            if (today.compareTo(valid) > 0)
            {
                throw new IllegalArgumentException("License expired.");
            }

            if (!todayHwkey.equals(hwkeyS))
            {
                throw new IllegalArgumentException("License is not for the hardware");
            }
        }
        catch (ParseException e)
        {
            throw new IllegalArgumentException("License is not valid");
        }
    }


    public License getLicense()
    {
        if (license == null)
        {
            license = new License();
            try
            {
                license.loadKeyRingFromResource("resources/pubring.gpg", digest);
                license.setLicenseEncodedFromFile(System.getProperty("user.dir") + File.separator + LICENSE_FILE_NAME);
            }
            catch (Exception e)
            {
                throw new IllegalArgumentException("License cannot be loaded",e);
            }
        }
        return license;
    }


    public static void main(String[] args)
    {
        LicenseChecker licenseChecker = new LicenseChecker();
        licenseChecker.check();
    }
}
