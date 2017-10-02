package com.example.timmy.splashactivity.Activity.Activity.Pager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.timmy.splashactivity.Activity.Activity.adapter.ItemBean2;
import com.example.timmy.splashactivity.Activity.Activity.adapter.Textimg2Adapter;
import com.example.timmy.splashactivity.Activity.Activity.adapter.firstPagerAdapter;
import com.example.timmy.splashactivity.Activity.Activity.base.BasePager;
import com.example.timmy.splashactivity.Activity.Activity.slide.LocalImageHolderView;
import com.example.timmy.splashactivity.Activity.Activity.slide.NetworkImageHolderView;
import com.example.commonlibrary.utils.LogUtil;
import com.example.timmy.splashactivity.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Timmy on 2017/7/9.
 */

public class firstpager extends BasePager implements AdapterView.OnItemClickListener, ViewPager.OnPageChangeListener, OnItemClickListener {


    private ListView mListview;
    private int[] imageidleft;
    private int []imageidright;
    private String[] name;




    private ConvenientBanner convenientBanner;//顶部广告栏控件
    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    private List<String> networkImages;
    private String[] images = {"http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://d.3987.com/sqmy_131219/001.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg"
    };

    // private ListView listView;
    private ArrayAdapter transformerArrayAdapter;
    private ArrayList<String> transformerList = new ArrayList<String>();

    private Textimg2Adapter adapter2;
    private List<ItemBean2> data2;



    private ListView listView;
    private String[] datas;
    private firstPagerAdapter mfirstPagerAdapter;
    public firstpager(Context content) {
        super(content);
    }

    @Override
    public View initVeiw() {
      LogUtil.e("首页已经被初始化了");
      View view=View.inflate(context, R.layout.activity_firstpage,null);
        convenientBanner = (ConvenientBanner)view.findViewById(R.id.convenientBanner);
        mListview = (ListView) view.findViewById(R.id.first_page_listview);
        mListview.setOnItemClickListener(this);

        //   listView = (ListView) findViewById(R.id.listView);
        transformerArrayAdapter = new ArrayAdapter(context, R.layout.adapter_transformer, transformerList);
        //listView.setAdapter(transformerArrayAdapter);
        //  listView.setOnItemClickListener(this);
        initdeson();

      //  mListview = (ListView)view.findViewById(R.id.listview);

        //getData();
       // initData();

        return view;
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //通过view获取其内部的组件，进而进行操作
        String text = (String) ((TextView)view.findViewById(R.id.text)).getText();
        //大多数情况下，position和id相同，并且都从0开始
        String showText = "点击第" + position + "项，文本内容为：" + text + "，ID为：" + id;
        Toast.makeText(context, showText, Toast.LENGTH_LONG).show();
    }

    @Override
    public void initdata() {
        super.initdata();
        LogUtil.e("首页的数据被初始化了");
       // initlistview();

        initImageLoader();
        loadTestDatas();
        convenientBanner.startTurning(5000);

        //本地图片例子
        convenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置指示器的方向
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                .setOnPageChangeListener(this)//监听翻页事件
                .setOnItemClickListener(this);

//        convenientBanner.setManualPageable(false);//设置不能手动影响

        //网络加载例子
        networkImages = Arrays.asList(images);
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, networkImages);


//手动New并且添加到ListView Header的例子
//        ConvenientBanner mConvenientBanner = new ConvenientBanner(this,false);
//        mConvenientBanner.setMinimumHeight(500);
//        mConvenientBanner.setPages(
//                new CBViewHolderCreator<LocalImageHolderView>() {
//                    @Override
//                    public LocalImageHolderView createHolder() {
//                        return new LocalImageHolderView();
//                    }
//                }, localImages)
//                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
//                        //设置指示器的方向
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                .setOnItemClickListener(this);
//        listView.addHeaderView(mConvenientBanner);

        data2 = new ArrayList<ItemBean2>();

        name=new String[]{"haha","haha"};
        imageidright=new int[]{R.drawable.web_icon_right_dis1,R.drawable.web_icon_right_dis1};
        imageidleft=new int[]{R.drawable.pass,R.drawable.pass};
        for(int i=0; i<2; i++){
            ItemBean2 bean = new ItemBean2(imageidleft[i],imageidright[i],name[i]);
            data2.add(bean);
        }

        adapter2 = new Textimg2Adapter(context, data2,R.layout.layout_item);

        mListview.setAdapter(adapter2);
        mListview.setOnItemClickListener(this);
    }

    private void initdeson() {
        String transforemerName = DefaultTransformer.class.getSimpleName();
        try {
            Class cls = Class.forName("com.ToxicBakery.viewpager.transforms." + transforemerName);
            ABaseTransformer transforemer = (ABaseTransformer) cls.newInstance();
            convenientBanner.getViewPager().setPageTransformer(true, transforemer);

            //部分3D特效需要调整滑动速度
            if (transforemerName.equals("StackTransformer")) {
                convenientBanner.setScrollDuration(1200);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    //初始化网络图片缓存库
    private void initImageLoader() {
        //网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                showImageForEmptyUri(R.drawable.ic_default_adimage)
                .cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context.getApplicationContext()).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

    /*
 加入测试Views
 * */
    private void loadTestDatas() {
        //本地图片集合
//        for (int position = 0; position < 5; position++)
//            localImages.add(getResId("ic_test_" + position, R.drawable.class));

//        //各种翻页效果
        // transformerList.add(DefaultTransformer.class.getSimpleName());
        //    transformerList.add(AccordionTransformer.class.getSimpleName());
//        transformerList.add(BackgroundToForegroundTransformer.class.getSimpleName());
//        transformerList.add(CubeInTransformer.class.getSimpleName());
//        transformerList.add(CubeOutTransformer.class.getSimpleName());
//        transformerList.add(DepthPageTransformer.class.getSimpleName());
//        transformerList.add(FlipHorizontalTransformer.class.getSimpleName());
//        transformerList.add(FlipVerticalTransformer.class.getSimpleName());
//        transformerList.add(ForegroundToBackgroundTransformer.class.getSimpleName());
//        transformerList.add(RotateDownTransformer.class.getSimpleName());
//        transformerList.add(RotateUpTransformer.class.getSimpleName());
//        transformerList.add(StackTransformer.class.getSimpleName());
//        transformerList.add(ZoomInTransformer.class.getSimpleName());
//        transformerList.add(ZoomOutTranformer.class.getSimpleName());

        transformerArrayAdapter.notifyDataSetChanged();
    }

    /**
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     *
     * @param variableName
     * @param c
     * @return
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 开始自动翻页
    @Override
    protected void onResume() {
        super.onResume();
        //开始自动翻页
        convenientBanner.startTurning(5000);
    }

    // 停止自动翻页
    @Override
    protected void onPause() {
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }

    //点击切换效果
   // @Override
  //  public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

//        点击后加入两个内容
//        localImages.clear();
//        localImages.add(R.drawable.ic_test_2);
//        localImages.add(R.drawable.ic_test_4);
//        convenientBanner.notifyDataSetChanged();

        //控制是否循环
//        convenientBanner.setCanLoop(!convenientBanner.isCanLoop());


//        String transforemerName = transformerList.get(position);
//        try {
//            Class cls = Class.forName("com.ToxicBakery.viewpager.transforms." + transforemerName);
//            ABaseTransformer transforemer = (ABaseTransformer) cls.newInstance();
//            convenientBanner.getViewPager().setPageTransformer(true, transforemer);
//
//            //部分3D特效需要调整滑动速度
//            if (transforemerName.equals("StackTransformer")) {
//                convenientBanner.setScrollDuration(1200);
//            }
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }

  //  }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Toast.makeText(context, "监听到翻到第" + position + "了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(context, "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
    }
}
