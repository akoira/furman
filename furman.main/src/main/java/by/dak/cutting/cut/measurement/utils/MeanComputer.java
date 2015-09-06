/*
 * To change this template, choose Tools | Templates and open the template in the draw.
 */

package by.dak.cutting.cut.measurement.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Peca
 */
public class MeanComputer
{

    private class MeasuredRecord
    {
        int ratingInvocations;
        int time;
        float bestRating;

        public MeasuredRecord(int ratingInvocations, float bestRating, int time)
        {
            this.ratingInvocations = ratingInvocations;
            this.bestRating = bestRating;
            this.time = time;
        }

        public float getBestRating()
        {
            return bestRating;
        }

        public void setBestRating(float bestRating)
        {
            this.bestRating = bestRating;
        }

        public int getRatingInvocations()
        {
            return ratingInvocations;
        }

        public void setRatingInvocations(int ratingInvocations)
        {
            this.ratingInvocations = ratingInvocations;
        }

        public int getTime()
        {
            return time;
        }

        public void setTime(int time)
        {
            this.time = time;
        }

    }

    private int averagingRecordCount = 10;

    private MeasuredRecord[] readData(String inputFileName) throws FileNotFoundException, IOException
    {
        ArrayList<MeasuredRecord> data = new ArrayList<MeanComputer.MeasuredRecord>();
        /*
		 * CsvReader reader = new CsvReader(inputFileName); reader.setDelimiter(';'); reader.readHeaders(); while
		 * (reader.readRecord()){ int ratingInvocations = Integer.parseInt(reader.get("rating_function_invocations"));
		 * float best_rating = Float.parseFloat(reader.get("best_rating")); int time =
		 * Integer.parseInt(reader.get("time")); if(ratingInvocations == 0)continue;
		 * 
		 * MeasuredRecord rec = new MeasuredRecord(ratingInvocations, best_rating, time); data.add(rec); }
		 * reader.close(); return data.toArray(new MeasuredRecord[data.size()]);
		 */
        return null;
    }

    private void sortRecords(MeasuredRecord[] records)
    {
        Arrays.sort(records, new Comparator<MeasuredRecord>()
        {

            public int compare(MeanComputer.MeasuredRecord rec1, MeanComputer.MeasuredRecord rec2)
            {
                int res = rec1.getRatingInvocations() - rec2.getRatingInvocations();
                return res;
            }

        });
    }

    private MeasuredRecord[] computeMean(MeasuredRecord[] records)
    {
        MeasuredRecord sumRecord = new MeasuredRecord(0, 0, 0);
        ArrayList<MeasuredRecord> meanData = new ArrayList<MeanComputer.MeasuredRecord>();
        int firstIndex = 0;
        int meanCount = this.averagingRecordCount;
        for (int lastIndex = 0; lastIndex < records.length; lastIndex++)
        {
            MeasuredRecord record = records[lastIndex];
            sumRecord.bestRating += record.bestRating;
            sumRecord.ratingInvocations += record.ratingInvocations;
            sumRecord.time += record.time;

            if (lastIndex >= firstIndex + meanCount)
            {
                MeasuredRecord firstRecord = records[firstIndex];
                sumRecord.bestRating -= firstRecord.bestRating;
                sumRecord.ratingInvocations -= firstRecord.ratingInvocations;
                sumRecord.time -= firstRecord.time;
                firstIndex++;

                MeasuredRecord averageRecord = new MeasuredRecord(0, 0, 0);
                averageRecord.bestRating = sumRecord.bestRating / meanCount;
                averageRecord.ratingInvocations = sumRecord.ratingInvocations / meanCount;
                averageRecord.time = sumRecord.time / meanCount;
                meanData.add(averageRecord);
            }
        }
        return meanData.toArray(new MeasuredRecord[meanData.size()]);
    }

    private void writeData(MeasuredRecord[] records, String fileName) throws IOException
    {
		/*
		 * CsvWriter writer = new CsvWriter(fileName); writer.setDelimiter(';'); writer.writeRecord(new
		 * String[]{"rating_function_invocations", "time", "best_rating"} ); for(MeasuredRecord record : records){
		 * String[] columns = new String[] { String.valueOf(record.ratingInvocations), String.valueOf(record.time),
		 * String.valueOf(record.bestRating) }; writer.writeRecord(columns); } writer.close();
		 */
    }

    public void compute(String inputFileName, String outputFileName) throws FileNotFoundException, IOException
    {
        MeasuredRecord[] inputData = readData(inputFileName);
        sortRecords(inputData);
        MeasuredRecord[] outputData = computeMean(inputData);
        writeData(outputData, outputFileName);
    }

    public int getAveragingRecordCount()
    {
        return averagingRecordCount;
    }

    public void setAveragingRecordCount(int averagingRecordCount)
    {
        this.averagingRecordCount = averagingRecordCount;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        ArgumentParser arguments = new ArgumentParser(args);
        if (!arguments.hasValue("-in") || !arguments.hasValue("-out"))
        {
            System.out.println("Too few arguments\n-in for input file\n-out for output file");
            return;
        }
        MeanComputer mc = new MeanComputer();

        String inputFileName = arguments.getStringValue("-in");
        String outputFileName = arguments.getStringValue("-out");
        mc.setAveragingRecordCount(arguments.getIntValue("-avgcnt", 200));

        System.out.println("Computing...");
        mc.compute(inputFileName, outputFileName);
        System.out.println("done");
    }
}
