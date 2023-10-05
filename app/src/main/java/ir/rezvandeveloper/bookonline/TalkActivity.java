package ir.rezvandeveloper.bookonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TalkActivity extends AppCompatActivity {

    AppCompatButton btnTalkQuestion,btnNewTalk,btnTalkWithoutResponse,btnMyTalk,btnMyFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);

        cast();

        btnTalkQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TalkActivity.this,TalkQuestionActivity.class);
                startActivity(intent);
            }
        });

        btnNewTalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TalkActivity.this,NewTalkingActivity.class);
                startActivity(intent);
            }
        });

        btnTalkWithoutResponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TalkActivity.this,WithoutResponseActivity.class);
                startActivity(intent);
            }
        });

        btnMyTalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TalkActivity.this,MyTalkActivity.class);
                startActivity(intent);
            }
        });

        btnMyFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TalkActivity.this,MyFavoriteActivity.class);
                startActivity(intent);
            }
        });
    }

    private void cast() {
        btnTalkQuestion = findViewById(R.id.btnTalkQuestion);
        btnNewTalk = findViewById(R.id.btnNewTalk);
        btnTalkWithoutResponse = findViewById(R.id.btnTalkWithoutResponse);
        btnMyTalk = findViewById(R.id.btnMyTalk);
        btnMyFavorite = findViewById(R.id.btnMyFavorite);
    }
}