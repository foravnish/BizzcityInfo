package sntinfotech.bizzcityinfo.Fragments;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sntinfotech.bizzcityinfo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FAQs extends Fragment {


    public FAQs() {
        // Required empty public constructor
    }
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_faqs, container, false);
        getActivity().setTitle("FAQ");

        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);


        return view;
    }


    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("What is your guarantee regarding the leads?");
        listDataHeader.add("Do I get my credit back if I don’t win the bid?");
        listDataHeader.add("Why should I pay rather than the person who wants the project done?");
        listDataHeader.add("How to report about a fraudulent posting or if a client does not respond? ");
        listDataHeader.add("How many contracts should I close? ");
        listDataHeader.add("How to get contact information for a project I want?");
        listDataHeader.add("I purchased few credits and it is still showing 0 credits");
        listDataHeader.add("How to bid on RFP once it is closed?");
        listDataHeader.add("On completing the project, how to get the payment?");
        listDataHeader.add("How is your site different from other freelancing sites?");
        listDataHeader.add("How to adjust the criteria for email?");
        listDataHeader.add("What is the amount of lead purchase?");


        // Adding child data
        List<String> top1 = new ArrayList<String>();
        top1.add("BizzCityInfo always guarantee quality. Well, we cannot assure that every client is good or end up dealing on a project with us, but we can certainly guarantee few things. In the business of design, your clients can find you from different sources like yellow pages, websites, google ads and many more. You will have some clients that are looking only for prices, but do not have enough funding. They might often want to get an idea of the cost. Everyone looks up and waits till they get a sale.\n" +
                "\n" +
                "The project leads are requested by a validate client. We have full time employees that look after the daily operations. We also have a department for customer service that works from Monday to Saturday and is responsible for validating the project through phone or email.\n" +
                "\n" +
                "Clients need to respond to two emails in the initial verification process. In addition to that, we also verify through phone calls before posting any project. If after posting, our client does not respond, we consider it to be bogus lead at BizzCityInfo. Accordingly, we refund the credit. The project is closed and reviewed by individuals. If you want, you can leave your feedback for any project and inform the support staff about your experience in the same.\n" +
                "\n" +
                "On the other hand, if the client does not choose us and closes the project, we do not consider it as a bogus lead. We handle each project on the basis of cases, and we try to be fair. We refund for a bogus project.\n" +
                "\n" +
                "We have introduced the new bidder acknowledgement system to approve the client with potential provider. For instance, if a client receives 3 to 4 bids, they might reject or ignore your bids and you get the credit back on the closing of the project.\n");


        List<String> top2 = new ArrayList<String>();
        top2.add("With our leads, you will get contact information. There is also no surety that you will get selected for the project. However, after purchasing the lead, there is no need to pay any extra fees or commissions.\n" +
                "\n" +
                "You can use the credits for purchasing information on the leads and the contact of the client. You can avail the opportunity to sell services, but we do not give any guarantee.\n" +
                "\n" +
                "We believe that BizzCityInfo offer a superior service ensuring quality and no commission to any middleman. We will not come in between you and your client so that you can work with them with several new projects. \n");


        List<String> top3 = new ArrayList<String>();
        top3.add("Well, it is a form of direct and cheaper alternative to advertising. Everyone pays for leads either through renting, marketing or advertising.\n" +
                "\n" +
                "Regardless the nature of business you have, you have to pay for leads through buying ads, printing cards, PPC ads and many more. It is nothing but the marketing cost and you are attracting your potential customers. However, once you add up the cost, you will find the cost of acquisition is low compared to the Google ads, newspapers ads or any other traditional forms of advertising.\n" +
                "\n" +
                "Many people build it along with the cost of managing business and you should model in such a way to cover the overall cost of the lead and make maximum profit.\n");

List<String> top4 = new ArrayList<String>();
        top4.add("With a bogus job posting, you will get your refund. Bogus leads will also be posted at frequent intervals. If it is found bogus, the credits of the projects are refunded.\n" +
                "\n" +
                "As you mail a bid and do not get any response, it does not mean that it is always bogus. You should leave your feedback. Try to reach the client, but if you can’t, you can notify us immediately with the help of our live support. We will investigate the matter.\n" +
                "\n" +
                "You can also use the integrated feedback system. When you purchase a lead, it gets added. After the closing date, clients get notification. When a project is closed, they enter the outcome. You will also get email and you can leave a feedback for the lead.\n" +
                "\n" +
                "When the closing date passes, it is mailed to every client. If a client does not close a project within 10 days we set it as under review and let you leave your feedback. As you leave the feedback, you can know about the response of the client or whether the project was bogus. Everyone who purchased the lead is also notified.\n");
List<String> top5 = new ArrayList<String>();
        top5.add("You should try to win around 20% of the total leads.\n" +
                "\n" +
                "Professional presentation and communication skills certainly help in winning 20% of the leads in purchases. The results might vary, but if you have a solid portfolio, work history and communication, you can win 1 of every 5 leads.\n" +
                "\n" +
                "It largely depends on the skills of sales and communications with the client. You should close 1 out of every 5 projects for bidding. Most jobs will not sell in all slots. However, BizzCityInfo will offer you a better chance. Some jobs are desirable and harder to win. If you do not achieve success, you should examine the client approach and review different methods, portfolio, techniques and even presentation. The closing rate should not be different. If you give up on bidding after two unsuccessful attempts, you will not get anywhere. On the other hand, if you bid on 10 jobs and still do not win, you should review the process.\n");
List<String> top6 = new ArrayList<String>();
        top6.add("Right after purchasing the lead, you will get client’s contact.\n" +
                "\n" +
                "However, clients first should acknowledge you with BizzCityInfo, and only then you would receive contact information.\n" +
                "\n" +
                "In this context, it is important to make sure that you first register following which you can purchase credits. You can use these credits for purchasing contact information.\n" +
                "\n" +
                "However, the recent trend is once you purchase leads, client will get notification about your bid. If he acknowledges, you will receive an email with the contact information. Thus, you do not end up purchasing one that has already been awarded. \n");
List<String> top7 = new ArrayList<String>();
        top7.add("Well, sometimes, Payment gateway takes time to update in our system. If the credits are not showing even after few hours, you can get in touch with us live or create a support ticket.\n" +
                "\n" +
                "I purchased 4 credits, but used only two. Your website says 0 credits available.\n" +
                "\n" +
                "Credits are based on the amount of estimated value of a project. Higher paying jobs will bring more credits.\n" +
                "\n" +
                "The price of a project determines the amount of credits it costs. Hence, check your credit history. You will get a link to check history after buying. If you find any problem, contact the customer support and we will surely investigate.\n" +
                "\n" +
                "Give me the criteria for establishing credibility of bids.\n" +
                "Anyone can post any project on our website, even if, it is amateur. The contact information is verified. We can even post questions to the client before the lead is purchased. \n");
List<String> top8 = new ArrayList<String>();
        top8.add("Bids are placed on the basis of first come first serve.\n" +
                "\n" +
                "We limit to 4 bids that benefits the clients of BizzCityInfo.\n" +
                "\n" +
                "You cannot purchase a closed RFP because we limit to 4. This is because too many people go after the same project. With us, you will have a better chance of landing the project and limiting the competition.\n" +
                "\n" +
                "What is expected from me when I am responding to RFP? I don’t know whether I am on the right track after purchasing couple of tracks. What kind of contact a client wants? Help me. \n" +
                "\n" +
                "The best thing that you can do is to sell your skills and maintain a strong portfolio. You should communicate with potential clients at the earliest.\n" +
                "\n" +
                "Every client is different, but you should emphasize on selling yourself. You should understand the need of 3 other people when you contact someone. You will have to draft right away but a portfolio of your work will be helpful.\n");
List<String> top9 = new ArrayList<String>();
        top9.add("You should work on the payment on an individual basis.\n" +
                "\n" +
                "You can take an initial payment before getting started and send the finished designs. It should be clear to both the parties from the beginning.\n" +
                "\n" +
                "You should talk about this issue with the person. You can send an invoice. It is also a good idea to get a partial deposit for the work. Do not send complete work to a client without receiving payment. This is a standard business practice that you should follow.\n" +
                "\n" +
                "We are connecting you to potential clients. It is you who will sell your services.\n");
List<String> top10 = new ArrayList<String>();
        top10.add("We offer low pricing for all the leads.\n" +
                "We do not take commission or charge any hidden fee. We will provide complete information on the client for the specific credit you have purchased.\n" +
                "\n" +
                "Unlike other websites, we do not charge any percentage of the work you do. We just offer a lead so that you get opportunity for the project. As you win the project, you will deal it, there is no middleman. You can also contact the client directly.\n" +
                "\n" +
                "Moreover, we also verify the lead before posting. Other lead services are about forwarding emails from the contact form due to which you end up with junk leads. We will provide only selected options.\n" +
                "\n" +
                "We do not allow unlimited bids, and hence the question of bidding wars does not arise. You get what you deserve in the industry.\n");
List<String> top11 = new ArrayList<String>();
        top11.add("You can log in to your account and click on My Alerts Tab. You can configure the setting for default email alerts.");
List<String> top12 = new ArrayList<String>();
        top12.add("The cost depends on the budget of a project. Credits will depend on how many you buy. You will even get free credits by purchasing in bulk.");

        listDataChild.put(listDataHeader.get(0), top1); // Header, Child data
        listDataChild.put(listDataHeader.get(1), top2);
        listDataChild.put(listDataHeader.get(2), top3);
        listDataChild.put(listDataHeader.get(3), top4);
        listDataChild.put(listDataHeader.get(4), top5);
        listDataChild.put(listDataHeader.get(5), top6);
        listDataChild.put(listDataHeader.get(6), top7);
        listDataChild.put(listDataHeader.get(7), top8);
        listDataChild.put(listDataHeader.get(8), top9);
        listDataChild.put(listDataHeader.get(9), top10);
        listDataChild.put(listDataHeader.get(10), top11);
        listDataChild.put(listDataHeader.get(11), top12);

    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context _context;
        private List<String> _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<String, List<String>> _listDataChild;

        public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                     HashMap<String, List<String>> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            final String childText = (String) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_item, null);
            }

            TextView txtListChild = (TextView) convertView
                    .findViewById(R.id.lblListItem);

            txtListChild.setText(childText);
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_group, null);
            }

            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.lblListHeader);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle);

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }



}
