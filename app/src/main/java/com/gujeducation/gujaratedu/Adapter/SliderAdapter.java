package com.gujeducation.gujaratedu.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Model.Slider;

import java.util.ArrayList;


public class SliderAdapter extends PagerAdapter {


    Functions mFunction;
    private ArrayList<Slider> IMAGES;
    private LayoutInflater inflater;
    private Context context;


    public SliderAdapter(Context context, ArrayList<Slider> IMAGES) {
        this.context = context;
        this.IMAGES = IMAGES;
        inflater = LayoutInflater.from(context);


    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.pager_item, view, false);
        mFunction = new Functions(context);

        assert imageLayout != null;
        final Slider item = IMAGES.get(position);

        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.img_pager_item);





        String strSliderBannerImage = IMAGES.get(position).getBannerImg().trim();
        String strSliderBannerURL = IMAGES.get(position).getBannerUrl().trim();


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(strSliderBannerURL));
                context.startActivity(browserIntent);
            }
        });


        try {
            //int color = getRandomColor(80);
            //  imageView.setBackgroundColor(color);

            if ((mFunction.ConvertImageURL(strSliderBannerImage) != null) && (!mFunction.ConvertImageURL(strSliderBannerImage).equals("")))
                Glide.with(context).load(mFunction.ConvertImageURL(strSliderBannerImage))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        //.placeholder(getRandomColor())
                        //.error(getRandomColor())
                        .priority(Priority.IMMEDIATE).dontAnimate()
                        //.crossFade(500)
                        .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        view.addView(imageLayout, 0);
        return imageLayout;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
