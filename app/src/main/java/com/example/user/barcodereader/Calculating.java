package com.example.user.barcodereader;




/**
 * Created by USER on 13.04.2018.
 */

public class Calculating {
    static String barcode;
    static int counter;
    static long summaEvenx3;
    static long summaEven;
    static long summaOdd;
    static long summaOddEven;
    static long last;
    static boolean answer;
    static long getLast;
    static long codeCountry;
    static String codeProd;
    static String codeProduct;
    static String discoverCountry;

    Calculating(String barcode, int counter) {
        this.barcode = barcode;
        this.counter = counter;
    }



    public static String typeBarcode(int counter) {
        if (counter == 8) {
            return "Штрих код относится к группе EAN-8 ";
        } else if (counter == 13) {
            return "Штрих код относится к группе EAN-13 ";
        } else if (counter == 10) {
            return "Штрих код относится к группе UPC-10 ";
        } else if (counter == 12) {
            return "Штрих код относится к группе UPC-12 ";
        } else if (counter == 14) {
            return "Штрих код относится к группе UPC-14 ";
        }
        return "Штрих код ни относится не к EAN-8, не к EAN-13, не к UPC-10, не к UPC-12, не к UPC-14.";
    }

    public static int counter(String barcode) {
        counter = barcode.length();
        return counter;
    }

    public static long checkBoxEven(String barcode) {
        long barcodeF = Long.parseLong(barcode);
        long summa = 0;
        long rest;
        barcodeF = barcodeF / 10;
        while (barcodeF > 0) {
            rest = barcodeF % 10;
            summa = summa + (rest);
            barcodeF = barcodeF / 10;
            barcodeF = barcodeF / 10;
        }
        return summa;
    }

    public static long x3(long summa) {
        long summax3 = summa * 3;
        return summax3;
    }

    public static long checkBoxOdd(String barcode) {
        long barcodeF = Long.parseLong(barcode);
        long summa = 0;
        long rest;
        barcodeF = barcodeF / 100;
        while (barcodeF > 0) {
            rest = barcodeF % 10;
            summa = summa + (rest);
            barcodeF = barcodeF / 10;
            barcodeF = barcodeF / 10;
        }
        return summa;
    }

    public static long checkBoxOddEven(long summaEvenx3, long summaOdd) {
        long summa = 0;
        summa = summaEvenx3 + summaOdd;
        return summa;
    }

    public static long last(String barcode) {
        long last;
        long barcodeF = Long.parseLong(barcode);
        last = barcodeF % 10;
        return last;
    }

    public static boolean test(long summaOddEven, long last) {

        if ((summaOddEven + last) % 10 == 0) {
            answer = true;
        } else {
            answer = false;
        }
        return answer;
    }

    public static long isGetLast(String barcode) {
        long barcodeF = Long.parseLong(barcode);
        barcodeF = barcodeF * 10;
        String barcodeF10 = String.valueOf(barcodeF);
        summaEven = checkBoxEven(barcodeF10);
        summaEvenx3 = x3(summaEven);
        summaOdd = checkBoxOdd(barcodeF10);
        summaOddEven = checkBoxOddEven(summaEvenx3, summaOdd);
        for (int i = 0; i < 10; i++) {
            if ((summaOddEven + i) % 10 == 0) {
                return i;
            }
        }
        return 0;
    }


    public static String isCodeProduct13(String barcode) {
        long barcodeF = Long.parseLong(barcode);
        long rest1;
        long rest2;
        long rest3;
        barcodeF = barcodeF / 10;
        codeProduct = "";
        rest1 = barcodeF % 10;
        barcodeF = barcodeF / 10;
        rest2 = barcodeF % 10;
        barcodeF = barcodeF / 10;
        rest3 = barcodeF % 10;
        codeProduct = codeProduct + rest3 + rest2 + rest1;
        return codeProduct;
    }

    public static String isCodeProduct8(String barcode) {

        String codeProductForReturn = "";
        char[] barCodeChar = barcode.toCharArray();
        for (int i = 5; i < 7; i++) {
            codeProductForReturn = codeProductForReturn + barCodeChar[i];
        }

        return codeProductForReturn;
    }

    public static String isCodeProd13(String barcode) {

        String codeProductForReturn = "";
        char[] barCodeChar = barcode.toCharArray();
        for (int i = 3; i < 9; i++) {
            codeProductForReturn = codeProductForReturn + barCodeChar[i];
        }

        return codeProductForReturn;
    }

    public static String isCodeProd8(String barcode) {

        String codeProductForReturn = "";
        char[] barCodeChar = barcode.toCharArray();
        for (int i = 3; i < 5; i++) {
            codeProductForReturn = codeProductForReturn + barCodeChar[i];
        }

        return codeProductForReturn;
    }

    public static String isCodeProd10(String barcode) {
        /*long barcodeF = Long.parseLong(barcode);
        long rest1 = 0;
        long rest2 = 0;
        long rest3 = 0;
        barcodeF = barcodeF / 10000;
        codeProd = "";
        rest1 = barcodeF % 10;
        barcodeF = barcodeF / 10;
        rest2 = barcodeF % 10;
        barcodeF = barcodeF / 10;
        rest3 = barcodeF % 10;
        codeProd = codeProd + rest3 + rest2+rest1;*/

        String codeProdForReturn = "";
        char[] barCodeChar = barcode.toCharArray();
        for (int i = 3; i < 6; i++) {
            codeProdForReturn = codeProdForReturn + barCodeChar[i];
        }

        return codeProdForReturn;
    }


    public static String isCodeProd12(String barcode) {


        String codeProductForReturn = "";
        char[] barCodeChar = barcode.toCharArray();
        for (int i = 3; i < 8; i++) {
            codeProductForReturn = codeProductForReturn + barCodeChar[i];
        }

        return codeProductForReturn;
    }
    public static String isCodeProd14(String barcode) {


        String codeProdForReturn = "";
        char[] barCodeChar = barcode.toCharArray();
        for (int i = 3; i < 10; i++) {
            codeProdForReturn = codeProdForReturn + barCodeChar[i];
        }

        return codeProdForReturn;
    }


    public static long isCodeCountry13(String  barcode) {
        String codeCountryForReturn = "";
        char[]barCodeChar = barcode.toCharArray();
        for (int i = 0; i < 3; i++) {
            codeCountryForReturn = codeCountryForReturn + barCodeChar[i];
        }

        Long codeCountryForReturnLong = Long.parseLong(codeCountryForReturn);

        return codeCountryForReturnLong;
    }

    public static long isCodeCountry8(String barcode) {

        String codeCountryForReturn = "";
        char[]barCodeChar = barcode.toCharArray();
        for (int i = 0; i < 3; i++) {
            codeCountryForReturn = codeCountryForReturn + barCodeChar[i];
        }

        Long codeCountryForReturnLong = Long.parseLong(codeCountryForReturn);

        return codeCountryForReturnLong;
    }



    public static String isDiscoverCountry(long codeCountry) {
        int i;
        for (i = 0; i < 140; i++) {
            if (codeCountry ==
                    i) {
                discoverCountry = "США";
                return discoverCountry;

            }

        }

        for (i = 300; i < 380; i++) {
            if (codeCountry == i) {
                discoverCountry = "Франция";
                return discoverCountry;
            }

        }

        for (i = 400; i < 441; i++) {
            if (codeCountry == i) {
                discoverCountry = "Германия";
                return discoverCountry;
            }

        }


        for (i = 460; i <= 469; i++) {
            if (codeCountry == i) {
                discoverCountry = "Россия";
                return discoverCountry;
            }

        }
        for (i = 500; i < 510; i++) {
            if (codeCountry == i) {
                discoverCountry = "Великобритания";
                return discoverCountry;
            }

        }

        for (i = 540; i < 550; i++) {
            if (codeCountry == i) {
                discoverCountry = "Люксембург или Бельгия";
                return discoverCountry;
            }

        }
        for (i = 570; i < 580; i++) {
            if (codeCountry == i) {
                discoverCountry = "Дания";
                return discoverCountry;
            }

        }
        for (i = 640; i < 650; i++) {
            if (codeCountry == i) {
                discoverCountry = "Финляндия";
                return discoverCountry;
            }

        }
        for (i = 700; i < 710; i++) {
            if (codeCountry == i) {
                discoverCountry = "Норвегия";
                break;
            }

        }
        for (i = 730; i < 740; i++) {
            if (codeCountry == i) {
                discoverCountry = "Швеция";
                break;
            }
        }

        for (i = 800; i < 840; i++) {
            if (codeCountry == i) {
                discoverCountry = "Италия";
                return discoverCountry;
            }
        }
        for (i = 840; i < 850; i++) {
            if (codeCountry == i) {
                discoverCountry = "Испания";
                return discoverCountry;
            }

        }
        for (i = 870; i < 880; i++) {
            if (codeCountry == i) {
                discoverCountry = "Нидерланды";
                return discoverCountry;
            }

        }
        for (i = 900; i < 920; i++) {
            if (codeCountry == i) {
                discoverCountry = "Австрия";
                return discoverCountry;
            }
        }
        for (i = 490; i < 500; i++) {
            if (codeCountry == i) {
                discoverCountry = "Япония";
                return discoverCountry;
            }

        }

        for (i = 690; i < 696; i++) {
            if (codeCountry == i) {
                discoverCountry = "Китай";
                return discoverCountry;
            }
        }
        for (i = 754; i < 756; i++) {
            if (codeCountry == i) {
                discoverCountry = "Канада";
                return discoverCountry;
            }
        }
        for (i = 789; i < 791; i++) {
            if (codeCountry == i) {
                discoverCountry = "Бразилия";
                return discoverCountry;
            }
        }
        for (i = 600; i < 602; i++) {
            if (codeCountry == i) {
                discoverCountry = "ЮАР";
                return discoverCountry;
            }
        }
        for (i = 930; i < 940; i++) {
            if (codeCountry == i) {
                discoverCountry = "Австралия";
                break;
            }
        }
        for (i = 940; i < 950; i++) {
            if (codeCountry == i) {
                discoverCountry = "Новая Зеландия";
                return discoverCountry;
            }
        }

        switch ((int)codeCountry){
            case 380: discoverCountry = "Болгария";
                break;
            case 383: discoverCountry = "Словения";
                break;
            case 385: discoverCountry = "Хорватия";
                break;
            case 387: discoverCountry = "Босния и Герцеговина";
                break;
            case 474: discoverCountry = "Эстония";
                break;
            case 475: discoverCountry = "Латвия";
                break;
            case 477: discoverCountry = "Литва";
                break;
            case 481: discoverCountry = "Беларусь";
                break;
            case 482: discoverCountry = "Укранина";
                break;
            case 484: discoverCountry = "Молдавия";
                break;
            case 520: discoverCountry = "Греция";
                break;
            case 530: discoverCountry = "Албания";
                break;
            case 531: discoverCountry = "Македония";
                break;
            case 535: discoverCountry = "Мальта";
                break;
            case 539: discoverCountry = "Ирландия";
                break;
            case 560: discoverCountry = "Люксембург";
                break;
            case 569: discoverCountry = "Португалия";
                break;
            case 590: discoverCountry = "Исландия";
                break;
            case 594: discoverCountry = "Румыния";
                break;
            case 599: discoverCountry = "Венгрия";
                break;
            case 470: discoverCountry = "Киргизия";
                break;
            case 476: discoverCountry = "Азербайджан";
                break;
            case 478: discoverCountry = "Узбекистан";
                break;
            case 479: discoverCountry = "Шри-Ланка";
                break;
            case 480: discoverCountry = "Филиппины";
                break;
            case 485: discoverCountry = "Армения";
                break;
            case 486: discoverCountry = "Грузия";
                break;
            case 487: discoverCountry = "Казахстан";
                break;
            case 488: discoverCountry = "Таджикистан";
                break;
            case 528: discoverCountry = "Ливан";
                break;
            case 529: discoverCountry = "Кипр";
                break;
            case 608: discoverCountry = "Бахрейн";
                break;
            case 621: discoverCountry = "Сирия";
                break;
            case 625: discoverCountry = "Иордания";
                break;
            case 626: discoverCountry = "Иран";
                break;
            case 627: discoverCountry = "Кувейт";
                break;
            case 628: discoverCountry = "Саудовская Аравия";
                break;
            case 629: discoverCountry = "ОАЭ";
                break;
            case 729: discoverCountry = "Израиль";
                break;
            case 865: discoverCountry = "Монголия";
                break;
            case 867: discoverCountry = "КНДР";
                break;
            case 868: discoverCountry = "Турция";
                break;
            case 869: discoverCountry = "Турция";
                break;
            case 880: discoverCountry = "Республика Корея";
                break;
            case 884: discoverCountry = "Камбоджа";
                break;
            case 885: discoverCountry = "Таиланд";
                break;
            case 888: discoverCountry = "Сингапур";
                break;
            case 858: discoverCountry = "Словакия";
                break;
            case 859: discoverCountry = "Чехия";
                break;
            case 860: discoverCountry = "Сербия";
                break;
            case 890: discoverCountry = "Индия";
                break;
            case 893: discoverCountry = "Вьетнам";
                break;
            case 899: discoverCountry = "Индонезия";
                break;
            case 955: discoverCountry = "Малайзия";
                break;
            case 740: discoverCountry = "Гватемала";
                break;
            case 741: discoverCountry = "Сальвадор";
                break;
            case 742: discoverCountry = "Гондурас";
                break;
            case 743: discoverCountry = "Никарагуа";
                break;
            case 744: discoverCountry = "Коста-Рика";
                break;
            case 745: discoverCountry = "Панама";
                break;
            case 746: discoverCountry = "Доминиканская Республика";
                break;
            case 750: discoverCountry = "Мексика";
                break;
            case 850: discoverCountry = "Куба";
                break;
            case 759: discoverCountry = "Венесуэла";
                break;
            case 770: discoverCountry = "Колумбия";
                break;
            case 773: discoverCountry = "Уругвай";
                break;
            case 775: discoverCountry = "Перу";
                break;
            case 777: discoverCountry = "Боливия";
                break;
            case 779: discoverCountry = "Аргентина";
                break;
            case 780: discoverCountry = "Чили";
                break;
            case 784: discoverCountry = "Парагвай";
                break;
            case 786: discoverCountry = "Эквадор";
                break;
            case 603: discoverCountry = "Гана";
                break;
            case 609: discoverCountry = "Маврикий";
                break;
            case 611: discoverCountry = "Марокко";
                break;
            case 613: discoverCountry = "Алжир";
                break;
            case 616: discoverCountry = "Кения";
                break;
            case 618: discoverCountry = "Кот-д'Ивуар";
                break;
            case 619:
                discoverCountry = "Тунис";
                break;
            case 622:
                discoverCountry = "Египет";
                break;
            case 489:
                discoverCountry = "Гонгонг";
                break;
            case 958:
                discoverCountry = "Макао";
                break;
            case 471:
                discoverCountry = "Тайвань";
                break;
            default:
                discoverCountry = "null";
                break;

        }

        return discoverCountry;
    }
}

