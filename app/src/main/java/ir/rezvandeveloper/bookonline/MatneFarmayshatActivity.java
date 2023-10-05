package ir.rezvandeveloper.bookonline;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

public class MatneFarmayshatActivity extends AppCompatActivity {

    AppCompatButton btnA,btnB,btnC,btnD,btnE,btnF,btnG,btnH,btnI,btnJ,btnK,btnL,btnM,btnN,btnO,btnP,btnQ,btnR,btnS,btnT,btnU,btnV;
    private final int EXTERNAL_MEMORY_REQUEST_CODE = 100;
    String URL,NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matne_farmayshat);

        cast();

        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MatneFarmayshatActivity.this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    NAME = "کتاب دائرة المعارف ظهور- ١.pdf";
                    URL = Api.LINK_MATNE_FARMAYESHAT_URL + NAME;
                    download(URL,NAME);
                }
            }
        });

        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MatneFarmayshatActivity.this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    NAME = "کتاب دائرة المعارف ظهور- ٢.pdf";
                    URL = Api.LINK_MATNE_FARMAYESHAT_URL + NAME;
                    download(URL,NAME);
                }
            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MatneFarmayshatActivity.this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    NAME = "کتاب دائرة المعارف ظهور- قسمت ٣.pdf";
                    URL = Api.LINK_MATNE_FARMAYESHAT_URL + NAME;
                    download(URL,NAME);
                }
            }
        });

        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MatneFarmayshatActivity.this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    NAME = "کتاب دائرة المعارف ظهور- قسمت ۴.pdf";
                    URL = Api.LINK_MATNE_FARMAYESHAT_URL + NAME;
                    download(URL,NAME);
                }
            }
        });

        btnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MatneFarmayshatActivity.this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    NAME = "94 ok مناسبتها.pdf";
                    URL = Api.LINK_MATNE_FARMAYESHAT_URL + NAME;
                    download(URL,NAME);
                }
            }
        });

        btnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MatneFarmayshatActivity.this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    NAME = "امام_شناسی_و_دین_شناسی_و_منجی_شناسی.pdf";
                    URL = Api.LINK_MATNE_FARMAYESHAT_URL + NAME;
                    download(URL,NAME);
                }
            }
        });

        btnG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MatneFarmayshatActivity.this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    NAME = "تفاسیر.pdf";
                    URL = Api.LINK_MATNE_FARMAYESHAT_URL + NAME;
                    download(URL,NAME);
                }
            }
        });

        btnH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MatneFarmayshatActivity.this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    NAME = "جبر و اختیار.pdf";
                    URL = Api.LINK_MATNE_FARMAYESHAT_URL + NAME;
                    download(URL,NAME);
                }
            }
        });

        btnI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MatneFarmayshatActivity.this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    NAME = "حسین قتلگاه تا حسین.pdf";
                    URL = Api.LINK_MATNE_FARMAYESHAT_URL + NAME;
                    download(URL,NAME);
                }
            }
        });

        btnJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MatneFarmayshatActivity.this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    NAME = "ذات شناسی.pdf";
                    URL = Api.LINK_MATNE_FARMAYESHAT_URL + NAME;
                    download(URL,NAME);
                }
            }
        });

        btnK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MatneFarmayshatActivity.this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    NAME = "شاه_کلیدهای_دائرة_المعارف_ظهور.pdf";
                    URL = Api.LINK_MATNE_FARMAYESHAT_URL + NAME;
                    download(URL,NAME);
                }
            }
        });

        btnL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MatneFarmayshatActivity.this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    NAME = "ظهورشناسی و سازندگی های ظهوری.pdf";
                    URL = Api.LINK_MATNE_FARMAYESHAT_URL + NAME;
                    download(URL,NAME);
                }
            }
        });

        btnM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MatneFarmayshatActivity.this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    NAME = "عدم تطابق ثبت با سند.pdf";
                    URL = Api.LINK_MATNE_FARMAYESHAT_URL + NAME;
                    download(URL,NAME);
                }
            }
        });

        btnN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MatneFarmayshatActivity.this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    NAME = "غیبت شناسی.pdf";
                    URL = Api.LINK_MATNE_FARMAYESHAT_URL + NAME;
                    download(URL,NAME);
                }
            }
        });

        btnO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MatneFarmayshatActivity.this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    NAME = "فایل فتلقی القرآن نهایی.pdf";
                    URL = Api.LINK_MATNE_FARMAYESHAT_URL + NAME;
                    download(URL,NAME);
                }
            }
        });

        btnP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MatneFarmayshatActivity.this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    NAME = "فتلقی القرآن مرداد 1402.pdf";
                    URL = Api.LINK_MATNE_FARMAYESHAT_URL + NAME;
                    download(URL,NAME);
                }
            }
        });

        btnQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MatneFarmayshatActivity.this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    NAME = "نمایه اسفند ماه.pdf";
                    URL = Api.LINK_MATNE_FARMAYESHAT_URL + NAME;
                    download(URL,NAME);
                }
            }
        });

        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MatneFarmayshatActivity.this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    NAME = "نمایه موضوعی ظهوریابها.pdf";
                    URL = Api.LINK_MATNE_FARMAYESHAT_URL + NAME;
                    download(URL,NAME);
                }
            }
        });

        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MatneFarmayshatActivity.this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    NAME = "پرسش و پاسخ -3.pdf";
                    URL = Api.LINK_MATNE_FARMAYESHAT_URL + NAME;
                    download(URL,NAME);
                }
            }
        });

        btnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MatneFarmayshatActivity.this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    NAME = "چشم انداز کلی.pdf";
                    URL = Api.LINK_MATNE_FARMAYESHAT_URL + NAME;
                    download(URL,NAME);
                }
            }
        });

        btnU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MatneFarmayshatActivity.this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    NAME = "کتاب حاوی تضادها(فتلقی القرآن).pdf";
                    URL = Api.LINK_MATNE_FARMAYESHAT_URL + NAME;
                    download(URL,NAME);
                }
            }
        });

        btnV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MatneFarmayshatActivity.this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    NAME = "کتاب ولی شناسی و دین شناسی.pdf";
                    URL = Api.LINK_MATNE_FARMAYESHAT_URL + NAME;
                    download(URL,NAME);
                }
            }
        });
    }

    private void download(String link,String name){
        DownloadManager downloadmanager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(link);

        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(name);
        request.setDescription("");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,name);
        downloadmanager.enqueue(request);
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale
                (MatneFarmayshatActivity.this, WRITE_EXTERNAL_STORAGE)) {
            reqPermission();
        } else {
            reqPermission();
        }
    }

    private void reqPermission() {
        ActivityCompat.requestPermissions(MatneFarmayshatActivity.this, new String[] {WRITE_EXTERNAL_STORAGE}, EXTERNAL_MEMORY_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == EXTERNAL_MEMORY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "مجوز تایید شد .", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(this, "مجوز رد شد .", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void cast(){
        btnA = findViewById(R.id.btnA);
        btnB = findViewById(R.id.btnB);
        btnC = findViewById(R.id.btnC);
        btnD = findViewById(R.id.btnD);
        btnE = findViewById(R.id.btnE);
        btnF = findViewById(R.id.btnF);
        btnG = findViewById(R.id.btnG);
        btnH = findViewById(R.id.btnH);
        btnI = findViewById(R.id.btnI);
        btnJ = findViewById(R.id.btnJ);
        btnK = findViewById(R.id.btnK);
        btnL = findViewById(R.id.btnL);
        btnM = findViewById(R.id.btnM);
        btnN = findViewById(R.id.btnN);
        btnO = findViewById(R.id.btnO);
        btnP = findViewById(R.id.btnP);
        btnQ = findViewById(R.id.btnQ);
        btnR = findViewById(R.id.btnR);
        btnS = findViewById(R.id.btnS);
        btnT = findViewById(R.id.btnT);
        btnU = findViewById(R.id.btnU);
        btnV = findViewById(R.id.btnV);
    }
}