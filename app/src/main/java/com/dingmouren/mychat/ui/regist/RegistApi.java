package com.dingmouren.mychat.ui.regist;

import io.reactivex.Observable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2018/4/23.
 */

public interface RegistApi {

    @POST
    @FormUrlEncoded
    Observable regist();
}
