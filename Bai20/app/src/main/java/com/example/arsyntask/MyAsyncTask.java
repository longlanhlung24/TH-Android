package com.example.arsyntask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
    private Context context;
    private ProgressBar progressBar;
    private TextView txtStatus;

    // Constructor để truyền vào ProgressBar và TextView từ MainActivity
    public MyAsyncTask(Context context, ProgressBar progressBar, TextView txtStatus) {
        this.context = context;
        this.progressBar = progressBar;
        this.txtStatus = txtStatus;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setProgress(0); // Đặt thanh tiến trình về 0
        txtStatus.setText("Bắt đầu...");
    }

    @Override
    protected Void doInBackground(Void... voids) {
        // Giả lập tiến trình chạy từ 0% đến 100%
        for (int i = 1; i <= 100; i++) {
            try {
                Thread.sleep(50); // Dừng 50ms để thấy tiến trình tăng dần
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress(i); // Gửi giá trị i để cập nhật giao diện
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressBar.setProgress(values[0]); // Cập nhật thanh tiến trình
        txtStatus.setText(values[0] + "%");  // Cập nhật phần trăm
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        txtStatus.setText("Hoàn thành!");
        Toast.makeText(context, "Xong rồi!", Toast.LENGTH_SHORT).show();
    }
}
