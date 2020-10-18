package com.codebuddy.util;

public class NumberToWordsConverter {
    public static final String[] units = { "", "ONE", "TWO", "THREE", "FOUR",
            "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN", "ELEVEN", "TWELVE",
            "THIRTEEN", "FOURTEEN", "FIFTEEN", "SIXTEEN", "SEVENTEEN",
            "EIGHTEEN", "NINETEEN" };

    public static final String[] tens = {
            "", 		// 0
            "",		// 1
            "TWENTY", 	// 2
            "THIRTY", 	// 3
            "FORTY", 	// 4
            "FIFTY", 	// 5
            "SIXTY", 	// 6
            "SEVENTY",	// 7
            "EIGHTY", 	// 8
            "NINETY" 	// 9
    };

    public static String convert(final int n) {
        if (n < 0) {
            return "Minus " + convert(-n);
        }

        if (n < 20) {
            return units[n] ;
        }

        if (n < 100) {
            return tens[n / 10] + ((n % 10 != 0) ? " " : "") + units[n % 10];
        }

        if (n < 1000) {
            return units[n / 100] + " HUNDRED" + ((n % 100 != 0) ? " " : "") + convert(n % 100);
        }

        if (n < 100000) {
            return convert(n / 1000) + " THOUSAND" + ((n % 10000 != 0) ? " " : "") + convert(n % 1000);
        }

        if (n < 10000000) {
            return convert(n / 100000) + " Lakh" + ((n % 100000 != 0) ? " " : "") + convert(n % 100000);
        }

        return convert(n / 10000000) + " Crore" + ((n % 10000000 != 0) ? " " : "") + convert(n % 10000000);
    }
}
