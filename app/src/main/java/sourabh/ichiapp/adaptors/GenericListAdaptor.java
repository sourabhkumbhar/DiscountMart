package sourabh.ichiapp.adaptors;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;

import java.util.List;

import sourabh.ichiapp.R;
import sourabh.ichiapp.activities.RetailerProfileAndCouponActivity;
import sourabh.ichiapp.app.AppController;
import sourabh.ichiapp.data.GenericData;
import sourabh.ichiapp.data.ShoppingCategoryData;
import sourabh.ichiapp.helper.Const;

/**
 * Created by Downloader on 2/24/2017.
 */

public class GenericListAdaptor extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<GenericData> genericDataList;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    GenericData m;
    private ShoppingCategoryData shoppingCategoryData;
    int position;
    boolean isService;
    public GenericListAdaptor(Activity activity,
                              List<GenericData> genericDataList,
                              boolean isService


    )
    {

        this.activity = activity;
        this.genericDataList = genericDataList;
        this.isService = isService;
    }




    @Override
    public int getCount() {
        return genericDataList.size();
    }

    @Override
    public Object getItem(int location) {
        return genericDataList.get(location);
    }

    @Override
    public long getItemId(int position)  {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        final String name,address, phone;
        this.position = position;
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        RelativeLayout list_layout = (RelativeLayout) convertView
                .findViewById(R.id.list_layout);
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView txtName = (TextView) convertView.findViewById(R.id.name);
        TextView txtAddress = (TextView) convertView.findViewById(R.id.address);
        TextView txtPhone = (TextView) convertView.findViewById(R.id.phone);
        ImageButton share = (ImageButton) convertView.findViewById(R.id.imageShare);


//        TextView year = (TextView) convertView.findViewById(R.id.releaseYear);

        // getting movie data for the row
        m = genericDataList.get(position);

        name = m.getName();
        address = m.getAddress();
        phone = String.valueOf(m.getPhone());
        // thumbnail image
        thumbNail.setImageUrl(m.getImage(), imageLoader);

        txtName.setText(name);
        txtAddress.setText(address);
        txtPhone.setText(phone);


        share.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                                         sharingIntent.setType("text/plain");




                                         String shareBody = name+"\n"+address+"\n"+phone+"\n\n"+"Share via ichiApp";
                                         sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share via ichiApp");
                                         sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                                         activity.startActivity(Intent.createChooser(sharingIntent, "Share via"));
                                     }
                                 }
        );


        list_layout.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {


                                        Intent i = new Intent(activity, RetailerProfileAndCouponActivity.class);

                                         i.putExtra(Const.KEY_RETAILERS,new Gson().toJson(genericDataList.get(position)));

                                         if(isService){
                                            i.putExtra(Const.KEY_IS_SERVICE,isService);
                                         }
                                         activity.startActivity(i);
                                     }
                                 }
        );



//        // txtPhone
//        String genreStr = "";
//        for (String str : m.getGenre()) {
//            genreStr += str + ", ";
//        }
//        genreStr = genreStr.length() > 0 ? genreStr.substring(0,
//                genreStr.length() - 2) : genreStr;
//        txtPhone.setText(genreStr);

        // release year
//        year.setText(String.valueOf(m.getPhone()));

        return convertView;
    }
}
