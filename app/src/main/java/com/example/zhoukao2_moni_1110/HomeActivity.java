package com.example.zhoukao2_moni_1110;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zhoukao2_moni_1110.Adapter.NewsAdapter;
import com.example.zhoukao2_moni_1110.bean.News;
import com.example.zhoukao2_moni_1110.callback.ICallBack;
import com.example.zhoukao2_moni_1110.utils.HttpUtils;
import com.google.gson.reflect.TypeToken;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ImageView imgSao;
    private int REQUEST_CODE=1000;
    private ViewPager vpBanner;
    private List<String> bannerlist;
    public static final int FLAG=123;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==FLAG){
                int currentItem = vpBanner.getCurrentItem();
                if (currentItem<bannerlist.size()-1){
                    currentItem++;
                }else{
                    currentItem=0;
                }
                vpBanner.setCurrentItem(currentItem);
                sendEmptyMessageDelayed(FLAG,2000);

            }

        }
    };
    private ListView lvNews;
    private List<News.DataBean> list;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        imgSao = findViewById(R.id.img_sao);
        vpBanner = findViewById(R.id.vp_banner);
        lvNews = findViewById(R.id.lv_news);

        list = new ArrayList<>();

        newsAdapter = new NewsAdapter(this,list);
        lvNews.setAdapter(newsAdapter);

        Type type=new TypeToken<News>(){}.getType();

        HttpUtils.getInstance().get("http://www.xieast.com/api/news/news.php", new ICallBack() {
            @Override
            public void onSuccess(Object obj) {
                News news= (News) obj;
                if (news!=null){
                    List<News.DataBean> data = news.getData();
                    if (data!=null){
                        list.clear();
                        list.addAll(data);
                        newsAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailed(Exception e) {

            }
        },type);

        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(HomeActivity.this,TwoActivity.class);
                startActivity(intent);
            }
        });

        bannerlist = new ArrayList<>();
        bannerlist.add("http://01.imgmini.eastday.com/mobile/20180512/20180512072505_0fe08f494e7c090764244e3581b3e5ca_5_mwpm_03200403.jpg");
        bannerlist.add("http://01.imgmini.eastday.com/mobile/20180512/20180512072505_0fe08f494e7c090764244e3581b3e5ca_1_mwpm_03200403.jpg");
        bannerlist.add("http://06.imgmini.eastday.com/mobile/20180512/20180512_38f5183808987be3783b180740d12a2a_cover_mwpm_03200403.jpg");

        vpBanner.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return bannerlist.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view==object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ImageView img=new ImageView(HomeActivity.this);
                Glide.with(HomeActivity.this).load(bannerlist.get(position)).into(img);
                container.addView(img);
                return img;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });
        handler.sendEmptyMessageDelayed(FLAG,2000);
        imgSao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(HomeActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
