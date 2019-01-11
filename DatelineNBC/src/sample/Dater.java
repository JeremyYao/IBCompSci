package sample;

public class Dater
{
    static String[] dow = {"Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat"};
    static final int[] daysPerMonth = { 31, 28, 31, 30, 31,30,31,31,30,31,30,31};
    static byte oct15dowIndex = 5; //is friday and is index
    static byte initday = 15;
    static byte initm = 10;
    static int inityr = 1582;

    public static String getDayofWeek(int m, int d, int yr)
    {
        int daysPassed = 0;

        if(inityr == yr && m == initm)
               return dow[(oct15dowIndex+d - initday) % 7];
        else if (d > daysPerMonth[m - 1] || (getLeapYearsInc(yr,yr) == 1 && m == 2 && d > 30))
            return "DNE";
        else
        {
            daysPassed += 31 - initday;

            for (int curryr = inityr; curryr < yr; curryr++)
                    daysPassed+= 365;

            for (int i = 0; i < m - 1; i++)
                daysPassed += daysPerMonth[i];

            if(m > 2 || (m == 2 && d == 29))
                daysPassed+= getLeapYearsInc(inityr + 1, yr);
            else
                daysPassed += getLeapYearsInc(inityr + 1, yr - 1);

            daysPassed += d;
        }
        return dow[(oct15dowIndex + daysPassed - 3)%7];
    }

    public static int getLeapYearsInc(int yrStart, int yrEnd)
    {
        int leapYears = 0;

        for (int i = yrStart; i <= yrEnd; i++)
        {
            if(i % 4 == 0)
            {
                if(i % 100 == 0)
                {
                    if (i % 400 == 0)
                        leapYears++;
                }
                else
                    leapYears++;
            }
        }
        return leapYears;
    }
}
