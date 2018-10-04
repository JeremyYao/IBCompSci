package sample;

import java.util.ArrayList;
import java.util.Arrays;

public class StatCalcLogic
{
    double[] data;

    public StatCalcLogic(double[] in)
    {
        data = in;
        Arrays.sort(data);
    }

    public StatCalcLogic(ArrayList<Double> in)
    {
        this.setData(in);
    }

    public double calcMean()
    {
        int sum = 0;

        for (int i = 0; i < data.length; i++)
        {
            sum += data[i];
        }

        return sum/data.length;
    }

    public double calcMedian()
    {
        if (data.length % 2 == 0)
        {
            return (data[data.length/2] + data[data.length/2 - 1])/2;
        }
        else
        {
            return data[data.length/2];
        }
    }

    public double calcFirstQuartile()
    {
        double ans;
        double[] dataTemp = data;
        double[] splitArray = new double[(int)(data.length/2 + .5)];

        for (int i = 0; i < (int)(data.length/2 + .5); i++)
            splitArray[i] = data[i];

        data = splitArray;
        ans = calcMedian();
        data = dataTemp;
        return ans;
    }

    public double calcThirdQuartile()
    {
        double ans;
        double[] dataTemp = data;
        double[] splitArray = new double[(int)(data.length/2 + .5)];

        for (int i = 0; i < (int)(data.length/2 + .5); i++)
            splitArray[i] = data[data.length - i - 1];

        data = splitArray;
        ans = calcMedian();

        data = dataTemp;
        return ans;
    }

    public double calcRange()
    {
        return data[data.length - 1] - data[0];
    }

    public ArrayList<Double> calcMode()
    {
        int repeats = 0;
        int repeatsMax = 0;

        ArrayList<Double> mostRepNums = new ArrayList<Double>();

        for (int i = 0; i < data.length-1; i++)
        {
            if(data[i] == data[i+1])
            {
                repeats++;
                if(repeats > repeatsMax)
                    repeatsMax = repeats;
            }
            else
                repeats = 0;
        }

        repeats = 0;

        for (int i = 0; i < data.length-1; i++)
        {
            if(data[i] == data[i+1])
            {
                repeats++;
                if(repeats == repeatsMax)
                    mostRepNums.add(data[i]);
            }
            else
                repeats = 0;
        }
        return mostRepNums;
    }

    public double calcSTD()
    {
        double mu = calcMean();
        double diffMeanSum = 0;

        for (int i = 0; i < data.length; i++)
        {
            diffMeanSum += Math.pow(data[i] - mu,2);
        }

        return Math.sqrt(diffMeanSum / (data.length-1));
    }

    public double calcCorrelationCoeff(StatCalcLogic in)
    {
        double zScoresProdSum = 0;
        double stdX = this.calcSTD();
        double stdY = in.calcSTD();
        double meanX = this.calcMean();
        double meanY = in.calcMean();
        if (this.getData().length == in.getData().length)
        {
            for (int i = 0; i < this.getData().length; i++)
            {
                zScoresProdSum += ((this.getData()[i] - meanX)/stdX)* ((in.getData()[i] - meanY)/stdY);
            }
            return zScoresProdSum/(this.getData().length-1);
        }
        else
            return 0;
    }

    public void setData(ArrayList<Double> dataIn)
    {
        if (dataIn.isEmpty())
        {
            double[] zeroes = {0.0};
            data = zeroes;
            return;
        }

        data = new double[dataIn.size()];
        for (int i = 0; i < dataIn.size(); i++)
        {
            data[i] = dataIn.get(i);
        }
        Arrays.sort(data);
    }

    public double[] getData()
    {
        return data;
    }
}
