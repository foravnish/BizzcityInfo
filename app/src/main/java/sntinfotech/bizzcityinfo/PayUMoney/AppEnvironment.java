package sntinfotech.bizzcityinfo.PayUMoney;


/**
 * Created by Rahul Hooda on 14/7/17.
 */
public enum AppEnvironment {

    SANDBOX {
        @Override
        public String merchant_Key() {

            return "BTkkbk";
//            return "2csQ7p";

        }

        @Override
        public String merchant_ID() {

//            return "472852";
            return "5288673";
        }

        @Override
        public String salt() {

            return "6dcquXTq";
//            return "QUWaQh0VWm";
        }

        @Override
        public String furl() {
            return "https://www.payumoney.com/mobileapp/payumoney/failure.php";
        }

        @Override
        public String surl() {
            return "https://www.payumoney.com/mobileapp/payumoney/success.php";
        }


        @Override
        public boolean debug() {
            return false;
        }
    },
    PRODUCTION {
        @Override
        public String merchant_Key() {
            return "BTkkbk";
        }

        @Override
        public String merchant_ID() {
            return "5288673";
        }

        @Override
        public String furl() {
            return "https://www.payumoney.com/mobileapp/payumoney/failure.php";
        }

        @Override
        public String surl() {
            return "https://www.payumoney.com/mobileapp/payumoney/success.php";
        }

        @Override
        public String salt() {
            return "6dcquXTq";
        }

        @Override
        public boolean debug() {
            return false;
        }
    };

    public abstract String merchant_Key();

    public abstract String merchant_ID();

    public abstract String furl();

    public abstract String surl();

    public abstract String salt();

    public abstract boolean debug();


}
