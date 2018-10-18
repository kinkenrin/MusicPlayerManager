# MusicPlayerManager
A customer view for android Music play

![image](https://raw.githubusercontent.com/kinkenrin/MusicPlayerManager/master/img/pic.gif)

## Using
1、Using gradle dependency:compile 'com.github.kinkenrin:musicplayview:1.0'

2、add to your layout.xml:

    
    <com.github.king.musicPlayView.MusicPlayView
        android:id="@+id/mpv_test"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="10dp"/>
        
3、in your activity add that code:

    MusicPlayView mpv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mpv_test = findViewById(R.id.mpv_test);
        mpv_test.setDataSource("/sdcard/netease/cloudmusic/Music/Ace.mp3");
    }

    @Override
    protected void onPause() {
        mpv_test.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mpv_test.resume();
    }



