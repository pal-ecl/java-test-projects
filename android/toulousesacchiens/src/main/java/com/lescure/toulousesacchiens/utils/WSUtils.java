package com.lescure.toulousesacchiens.utils;

import com.google.gson.Gson;
import com.lescure.toulousesacchiens.model.RecordBean;
import com.lescure.toulousesacchiens.model.ResultBean;
import com.lescure.toulousesacchiens.utils.OkHttpUtil;

import java.util.ArrayList;

public class WSUtils {

    private static String URL_WS_DOG_BAG = "https://data.toulouse-metropole.fr/api/records/" +
            "1.0/search/?dataset=distributeurs-de-sacs-pour-dejections-canines&q=";
    private static String URL_WS_DOG_BAG_ROWS = "&rows=200";

    public static ArrayList<RecordBean> findDogBagFromWeb(String search) throws Exception {

        String myStringJson = OkHttpUtil.sendGetOkHttpRequest(URL_WS_DOG_BAG + search + URL_WS_DOG_BAG_ROWS);

        Gson gson = new Gson();

        ResultBean result = gson.fromJson(myStringJson, ResultBean.class);

        return result.getRecords();
    }
}
