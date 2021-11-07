package kr.ac.deu.se.example;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class NaverMapActivity extends AppCompatActivity implements OnMapReadyCallback {
  NaverMap map;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_naver_map);

    FragmentManager fm = getSupportFragmentManager();
    MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
    if (mapFragment == null) {
      mapFragment = MapFragment.newInstance();
      fm.beginTransaction().add(R.id.map, mapFragment).commit();
    }

    mapFragment.getMapAsync(this);
  }

  @UiThread
  @Override
  public void onMapReady(@NonNull NaverMap naverMap) {
    map = naverMap;
    map.setMapType(NaverMap.MapType.Basic);

    LatLng busan = new LatLng(35.157702, 129.059164);
    map.moveCamera(CameraUpdate.scrollTo(busan));
    map.moveCamera(CameraUpdate.zoomTo(10.0));

    String api =
        "https://www.data.go.kr/cmm/cmm/fileDownload.do?atchFileId=FILE_000000002410759&fileDetailSn=1";

    new DownloadFileTask().execute(api);
  }

  private class DownloadFileTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urls) {
      try {
        URL url = new URL(urls[0]);
        return Downloader.downloadFile(url);
      } catch (IOException e) {
        return e.getMessage();
      }
    }

    protected void onPostExecute(String result) {
      try {
        BufferedReader reader = new BufferedReader(new StringReader(result));

        String line = reader.readLine();
        while ((line = reader.readLine()) != null) {
          String[] words = line.split(",");
          String name = words[0];
          LatLng position =
              new LatLng(
                  Double.parseDouble(words[words.length - 2]),
                  Double.parseDouble(words[words.length - 1]));

          Marker marker = new Marker();
          marker.setPosition(position);
          marker.setMap(map);

          marker.setSubCaptionText(name);
        }
      } catch (Exception ignored) {
      }
    }
  }

  private static class Downloader {
    public static String downloadFile(URL url) throws IOException {
      HttpURLConnection connection = null;
      try {
        connection = (HttpURLConnection) url.openConnection();
        BufferedInputStream buf = new BufferedInputStream(connection.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(buf, "cp949"));

        String line = "";
        StringBuilder page = new StringBuilder();
        while ((line = reader.readLine()) != null) {
          page.append(line).append("\n");
        }

        return page.toString();
      } finally {
        Objects.requireNonNull(connection).disconnect();
      }
    }
  }
}
