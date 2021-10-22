package com.gujeducation.gujaratedu.ServerAPIs;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.gujeducation.gujaratedu.Application.AppController;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.ProgressLoadingView;
import com.gujeducation.gujaratedu.Interface.OnResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;


public class APIs {

    static ProgressLoadingView mView = null;
    //static AVLoadingIndicatorView mView = null;


    public static void callAPI(final AppCompatActivity activity, final OnResult onresult, String url) {
        try {
            Log.e("GetRequest-->", url);
            if (activity != null) {
                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (activity != null) {
                            if (mView != null)
                                mView.dismiss();
                            mView = new ProgressLoadingView();
                            mView.show(activity.getSupportFragmentManager(), "load");
                        }
                    }
                });
            }
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if (mView != null && !mView.isHidden())
                                mView.dismissAllowingStateLoss();
                                //mView.dismiss();
                            else
                                Log.e("mView", "Error");
                            //Log.e("APIGETResponse", response.toString());
                            onresult.onResult(response);
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (mView != null)
                                mView.dismiss();
                            VolleyLog.e("VolleyLogGET", "Error: " + error.getMessage());
                            if (activity != null) {
                                Toast.makeText(activity.getApplicationContext(), "Something went wrong..", Toast.LENGTH_SHORT).show();
                            }
                            onresult.onResult(null);
                        }
                    });
            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            jsonObjReq.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });
            jsonObjReq.setShouldCache(false);
            AppController.getInstance().addToRequestQueue(jsonObjReq);
            //          AppController.getInstance().getRequestQueue().getCache().remove(url);
            // AppController.getInstance().getRequestQueue().getCache().invalidate(url, true);
        } catch (Exception e) {
            e.printStackTrace();
//            Log.e("catch_callGetAPI", e.getMessage());

        }
    }

    public static void callPostAPI(AppCompatActivity activity, final OnResult onresult, String url, JSONObject job) {

        try {
            //Log.e("API->PostRequest", url + "\n" + job.toString());
            try {

                if (activity != null) {
                    if (mView != null)
                        mView.dismiss();
                    mView = new ProgressLoadingView();
                    mView.show(activity.getSupportFragmentManager(), "load");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            JSONObject parameters = new JSONObject();
            if (job != null)
                parameters = job;
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    url, parameters,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //Log.e("APIPOSTResponse", response.toString());
                            onresult.onResult(response);
                            try {
                                if (mView != null)
                                    mView.dismiss();
                            } catch (Exception ex) {
                                Log.e("Exception_res", ex.getMessage());
                            }

                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (mView != null)
                                mView.dismiss();
                            VolleyLog.e("VolleyLogPOST", "Error: " + error.getMessage());
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
//                                    Toast.makeText(activity, "Something went wrong..", Toast.LENGTH_SHORT).show();
                                }
                            }).start();
                            onresult.onResult(null);
                        }
                    });
            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            jsonObjReq.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });

//            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
//                    0,
//                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            jsonObjReq.setShouldCache(false);
            AppController.getInstance().addToRequestQueue(jsonObjReq);
            //AppController.getInstance().getRequestQueue().getCache().remove(url);
            //AppController.getInstance().getRequestQueue().getCache().invalidate(url, true);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("catch_callPostAPI", e.getMessage());
        }
    }


    public static void getAuthorizeCardList(AppCompatActivity activity, OnResult onresult) {
        Uri uri = new Uri.Builder().scheme("https")
                .authority(Connection.SERVER_GETURL)
                .path("authorizenetlist.php")
                // .appendQueryParameter("Page",cms)
                .build();
        String query = String.valueOf(uri);
        //Log.e("getBanners", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }


    public static void getBanners(AppCompatActivity activity, OnResult onresult, int customerid) {
        Uri uri = new Uri.Builder().scheme("https")
                .authority(Connection.SERVER_GETURL)
                .path("banners.php?cust_id=" + customerid)
                // .appendQueryParameter("Page",cms)
                .build();
        String query = String.valueOf(uri);
        //Log.e("getBanners", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getBestSellers(AppCompatActivity activity, OnResult onresult) {
        Uri uri = new Uri.Builder().scheme("https")
                .authority(Connection.SERVER_GETURL)
                .path("bestSeller.php")
                // .appendQueryParameter("Page",cms)
                .build();
        String query = String.valueOf(uri);
        //Log.e("getBestSellers", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getMenu(AppCompatActivity activity, OnResult onresult) {
        Uri uri = new Uri.Builder().scheme("https")
                .authority(Connection.SERVER_GETURL)
                .path("menu.php")
                // .appendQueryParameter("Page",cms)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getCurrency(AppCompatActivity activity, OnResult onresult) {
        Uri uri = new Uri.Builder().scheme("https")
                .authority(Connection.SERVER_GETURL)
                .path("currency.php")
                // .appendQueryParameter("Page",cms)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getNewArrival(AppCompatActivity activity, OnResult onresult) {
        Uri uri = new Uri.Builder().scheme("https")
                .authority(Connection.SERVER_GETURL)
                .path("NewIn.php")
                // .appendQueryParameter("Page",cms)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }


    public static void addCoupon(AppCompatActivity activity, OnResult onresult, String id, String couponcode) {
        String query = Connection.SERVER_POSTURL + "coupon.php?action=add";

        JSONObject jo = new JSONObject();
        try {
            jo.put("user_id", id);
            jo.put("couponcode", couponcode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        APIs.callPostAPI(activity, onresult, query, jo);
    }

    public static void deleteCoupon(AppCompatActivity activity, OnResult onresult, String id) {
        String query = Connection.SERVER_POSTURL + "coupon.php?action=delete";

        JSONObject jo = new JSONObject();
        try {
            jo.put("user_id", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        APIs.callPostAPI(activity, onresult, query, jo);
    }

    public static void addProductToWishList(AppCompatActivity activity, OnResult onresult, String product_id, String customer_id) {
        String query = Connection.SERVER_POSTURL + "Wishlist.php?action=add";

        JSONObject jo = new JSONObject();
        try {
            jo.put("product_id", product_id);
            jo.put("customer_id", customer_id);


        } catch (Exception e) {
            e.printStackTrace();
        }
        APIs.callPostAPI(activity, onresult, query, jo);


    }

    public static void removeProductFromWishList(AppCompatActivity activity, OnResult onresult, String product_id, String customer_id) {
        String query = Connection.SERVER_POSTURL + "Wishlist.php?action=delete";

        JSONObject jo = new JSONObject();
        try {
            jo.put("product_id", product_id);
            jo.put("customer_id", customer_id);


        } catch (Exception e) {
            e.printStackTrace();
        }
        APIs.callPostAPI(activity, onresult, query, jo);
    }

    public static void viewWishListProduct(AppCompatActivity activity, OnResult onresult, String customer_id) {
        String query = Connection.SERVER_POSTURL + "Wishlist.php?action=view";

        JSONObject jo = new JSONObject();
        try {
            jo.put("customer_id", customer_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        APIs.callPostAPI(activity, onresult, query, jo);
    }


    public static void getCountries(AppCompatActivity activity, OnResult onresult) {
        Uri uri = new Uri.Builder().scheme("https")
                .authority(Connection.SERVER_GETURL)
                .path("countrylist.php")
                // .appendQueryParameter("Page",cms)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }


    public static void getRegions(AppCompatActivity activity, OnResult onresult, String country_id) {
        String query = Connection.SERVER_POSTURL + "regions.php";

        JSONObject jo = new JSONObject();
        try {
            jo.put("country_id", country_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        APIs.callPostAPI(activity, onresult, query, jo);
    }

    public static void addressList(AppCompatActivity activity, OnResult onresult, int user_id) {
        String query = Connection.SERVER_POSTURL + "myAccount.php?action=AddressList";

        JSONObject jo = new JSONObject();
        try {
            jo.put("user_id", user_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        APIs.callPostAPI(activity, onresult, query, jo);
    }


    public static void addressAddNew(AppCompatActivity activity, OnResult onresult, int cust_id, String firstname,
                                     String lastname, String country, String region, String region_id, String city,
                                     String street1, String street2, String telephone, String postcode,
                                     boolean is_default_billing, boolean is_default_shipping) {
        String query = Connection.SERVER_POSTURL + "myAccount.php?action=NewAddress";

        JSONObject jo = new JSONObject();
        try {
            jo.put("cust_id", cust_id);
            jo.put("firstname", firstname);
            jo.put("lastname", lastname);
            jo.put("country", country);
            jo.put("region", region);
            jo.put("region_id", region_id);
            jo.put("city", city);
            jo.put("street1", street1);
            jo.put("street2", street2);
            jo.put("telephone", telephone);
            jo.put("postcode", postcode);
            jo.put("is_default_billing", is_default_billing);
            jo.put(",is_default_shipping", is_default_shipping);
        } catch (Exception e) {
            e.printStackTrace();
        }
        APIs.callPostAPI(activity, onresult, query, jo);
    }


    public static void addressUpdate(AppCompatActivity activity, OnResult onresult,
                                     int address_id, String firstname, String lastname,
                                     String country, String region, String region_id, String city, String street1,
                                     String street2, String telephone, String postcode,
                                     boolean is_default_billing, boolean is_default_shipping) {
        String query = Connection.SERVER_POSTURL + "myAccount.php?action=UpdateAddress";

        JSONObject jo = new JSONObject();
        try {
            jo.put("address_id", address_id);
            jo.put("firstname", firstname);
            jo.put("lastname", lastname);
            jo.put("country", country);
            jo.put("region", region);
            jo.put("region_id", region_id);
            jo.put("city", city);
            jo.put("street1", street1);
            jo.put("street2", street2);
            jo.put("telephone", telephone);
            jo.put("postcode", postcode);
            jo.put("is_default_billing", is_default_billing);
            jo.put("is_default_shipping", is_default_shipping);
        } catch (Exception e) {
            e.printStackTrace();
        }
        APIs.callPostAPI(activity, onresult, query, jo);
    }


    public static void addressDelete(AppCompatActivity activity, OnResult onresult, int customer_id, int address_id) {
        String query = Connection.SERVER_POSTURL + "myAccount.php?action=DeleteAddress";

        JSONObject jo = new JSONObject();
        try {
            jo.put("address_id", address_id);
            jo.put("cust_id", customer_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        APIs.callPostAPI(activity, onresult, query, jo);
    }


    public static void getOrderList(AppCompatActivity activity, OnResult onresult, int cust_id) {
        String query = Connection.SERVER_POSTURL + "myAccount.php?action=OrderDetail";

        JSONObject jo = new JSONObject();
        try {
            jo.put("cust_id", cust_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        APIs.callPostAPI(activity, onresult, query, jo);
    }

    public static void getOrderDetail(AppCompatActivity activity, OnResult onresult, int order_id, int cust_id) {
        String query = Connection.SERVER_POSTURL + "myAccount.php?action=ViewOrder";

        JSONObject jo = new JSONObject();
        try {
            jo.put("cust_id", cust_id);
            jo.put("order_id", order_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        APIs.callPostAPI(activity, onresult, query, jo);
    }

    public static void ReOrder(AppCompatActivity activity, OnResult onresult, int order_id, int cart_id) {
        String query = Connection.SERVER_POSTURL + "myAccount.php?action=ReOrder";

        JSONObject jo = new JSONObject();
        try {
            jo.put("order_id", order_id);
            jo.put("cart_id", cart_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        APIs.callPostAPI(activity, onresult, query, jo);
    }

    //stiching object
    public static void addCart(AppCompatActivity activity, OnResult onresult, String user_id, int product_id, int qty, JSONObject joStiching,
                               JSONObject joStandardSize, String type) {
        String query = Connection.SERVER_POSTURL + "cart.php?action=add";
        //Log.e("APItype", "type->>" + type + " lblStitchType->>" + lblStitchType);
        JSONObject jo = new JSONObject();
        try {
            jo.put("user_id", user_id);
            jo.put("product_id", product_id);
            jo.put("qty", qty);
            if (type.equalsIgnoreCase("sizeobj"))
                jo.put("size", joStiching);
            else if (type.equalsIgnoreCase("stitchingobj")) {
                jo.put("stiching", joStiching);
                jo.put("standardsize", joStandardSize);
                //jo.put("label", lblStitchType);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        APIs.callPostAPI(activity, onresult, query, jo);
    }

    //without stiching details
    public static void addCartSimple(AppCompatActivity activity, OnResult onresult, String user_id, int product_id, int qty) {
        String query = Connection.SERVER_POSTURL + "cart.php?action=add";
        JSONObject jo = new JSONObject();
        try {
            jo.put("user_id", user_id);
            jo.put("product_id", product_id);
            jo.put("qty", qty);
            //jo.put("label", "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        APIs.callPostAPI(activity, onresult, query, jo);
    }


    public static void getCheckoutMethods(AppCompatActivity activity, OnResult onresult, int user_id, int ship_id, int bill_id) {
        String query = Connection.SERVER_POSTURL + "checkoutmethods.php";

        JSONObject jo = new JSONObject();
        try {
            jo.put("user_id", user_id);
            jo.put("ship_id", ship_id);
            jo.put("bill_id", bill_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        APIs.callPostAPI(activity, onresult, query, jo);
    }


    public static void checkout(AppCompatActivity activity, OnResult onresult, String user_id, String ship_code, String pay_code, String ship_id, String bill_id) {
        String query = Connection.SERVER_POSTURL + "checkout.php";

        JSONObject jo = new JSONObject();
        try {
            jo.put("user_id", user_id);
            jo.put("ship_code", ship_code);
            jo.put("pay_code", pay_code);
            jo.put("ship_id", ship_id);
            jo.put("bill_id", bill_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        APIs.callPostAPI(activity, onresult, query, jo);
    }


    public static void getCartList(AppCompatActivity activity, OnResult onresult, int user_id) {
        String query = Connection.SERVER_POSTURL + "cart.php?action=list";

        JSONObject jo = new JSONObject();
        try {
            jo.put("user_id", user_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        APIs.callPostAPI(activity, onresult, query, jo);
    }


    public static void cartCount(AppCompatActivity activity, OnResult onresult, String user_id) {
        String query = Connection.SERVER_POSTURL + "cartinfo.php";
        JSONObject jo = new JSONObject();
        try {
            jo.put("user_id", user_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        APIs.callPostAPI(activity, onresult, query, jo);

    }

    public static void updateCart(AppCompatActivity activity, OnResult onresult, int user_id, int product_id, int qty) {
        String query = Connection.SERVER_POSTURL + "cart.php?action=update";

        JSONObject jo = new JSONObject();
        try {
            jo.put("user_id", user_id);
            jo.put("product_id", product_id);
            jo.put("qty", qty);
        } catch (Exception e) {
            e.printStackTrace();
        }

        APIs.callPostAPI(activity, onresult, query, jo);

    }

    public static void deleteCart(AppCompatActivity activity, OnResult onresult, int user_id, int product_id) {
        String query = Connection.SERVER_POSTURL + "cart.php?action=delete";

        JSONObject jo = new JSONObject();
        try {
            jo.put("user_id", user_id);
            jo.put("product_id", product_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        APIs.callPostAPI(activity, onresult, query, jo);
    }

    public static void filterList(AppCompatActivity activity, OnResult onresult, String category_id) {
        String query = Connection.SERVER_POSTURL + "filter_options.php";

        JSONObject jo = new JSONObject();
        try {
            jo.put("category_id", category_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Log.e("filterList", "Calling With:" + jo.toString());
        APIs.callPostAPI(activity, onresult, query, jo);
    }


    public static void stichingList(AppCompatActivity activity, OnResult onresult, int product_id) {
        String query = Connection.SERVER_POSTURL + "Stiching.php";

        JSONObject jo = new JSONObject();
        try {
            jo.put("product_id", product_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        APIs.callPostAPI(activity, onresult, query, jo);
    }


    /*
    * "category_id":"49",
  "start":0,
  "sort":"entity_id-desc",
  "work":"0",
  "color":"0",
  "size":"0",
  "fabric":"0",
  "price_from":"0",
  "price_to":"0",
  "occasion":"0",
  "time_to_dispatch":"0",
  "celebrity":"0",
  "searchText":"",
  "customer_id":"256"*/
    public static void getProductList(AppCompatActivity activity, OnResult onresult,
                                      String menuid,
                                      int start,
                                      String sort, String work, String color, String size,
                                      String fabric, String price_from, String price_to,
                                      String occasion, String time_to_dispatch, String celebrity,
                                      String searchText, String customer_id) {
        try {
            String query = Connection.SERVER_POSTURL + "collection.php";
            JSONObject jo = new JSONObject();
            try {
                jo.put("category_id", menuid);
                jo.put("start", start);
                jo.put("sort", sort);
                jo.put("work", work);
                jo.put("color", color);
                jo.put("size", size);
                jo.put("fabric", fabric);
                jo.put("price_from", price_from);
                jo.put("price_to", price_to);
                jo.put("occasion", occasion);
                jo.put("time_to_dispatch", time_to_dispatch);
                jo.put("celebrity", celebrity);
                jo.put("searchText", searchText);
                jo.put("customer_id", customer_id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Log.e("sendProductList", "Calling With:" + jo.toString());
            APIs.callPostAPI(activity, onresult, query, jo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ////https://sandip-pc/members/sandip/hakoba/mobile/UserFunctions.php?Service=login


//http://ssamsbsurat.org/android_api/login.php?student_uid=242215002021910001&student_password=12345678
    /*public static void doLogin(AppCompatActivity activity, OnResult onresult,
                               String student_uid,
                               String student_password) {
        try {
            String query = Connection.SERVER_GETURL + "login.php?";
            JSONObject jo = new JSONObject();
            try {
                jo.put("student_uid", student_uid);
                jo.put("student_password", student_password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("doLogin", "Calling With:" + jo.toString());
            APIs.callPostAPI(activity, onresult, query, jo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void doLogin(AppCompatActivity activity, OnResult onresult, String student_uid, String student_password) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("login.php?student_uid=" + student_uid + "&student_password=" + student_password)
                .build();
        String query = String.valueOf(uri);
        //  Log.e("doLogin", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }*/

    public static void getStudentProfile(AppCompatActivity activity, OnResult onresult, String student_uid) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getStudentProfile.php?student_id=" + student_uid)
                .build();
        String query = String.valueOf(uri);
        //  Log.e("doLogin", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }


    public static void UpdateMobileNo(AppCompatActivity activity, OnResult onresult, String student_uid, String student_mobileno) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("udateProfile.php?student_id=" + student_uid + "&student_mobileno=" + student_mobileno)
                .build();
        String query = String.valueOf(uri);
        //  Log.e("doLogin", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void Feedback(AppCompatActivity activity, OnResult onresult, String category, String mobileno, String fullname,
                                String schoolname, String emailid, String rating, String feedbackmsg) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("feedback.php?category=" + category
                        + "&mobileno=" + mobileno
                        + "&name=" + fullname
                        + "&schoolname=" + schoolname
                        + "&emailid=" + emailid
                        + "&rating=" + rating
                        + "&feedback=" + feedbackmsg)
                .build();
        String query = String.valueOf(uri);
        //  Log.e("doLogin", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }


    public static void getTextbookList(AppCompatActivity activity, OnResult onresult, String medium_id, String standard_id) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getTextbookList.php?mediumId=" + medium_id + "&standardId=" + standard_id)
                .build();
        String query = String.valueOf(uri);
        //Log.e("getTextbookList", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getChapterList(AppCompatActivity activity, OnResult onresult, String medium_id, String standard_id, String subject_id) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getChapterList.php?mediumId=" + medium_id + "&standardId=" + standard_id + "&subjectId=" + subject_id)
                .build();
        String query = String.valueOf(uri);
        //Log.e("getChapterList", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }


    public static void getSemesterList(AppCompatActivity activity, OnResult onresult, String medium_id) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getSemester.php?mediumId=" + medium_id)
                .build();
        String query = String.valueOf(uri);
        //Log.e("getSubject", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getStandardList(AppCompatActivity activity, OnResult onresult) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getStandard.php")
                .build();
        String query = String.valueOf(uri);
        //Log.e("getSubject", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getSubjectList(AppCompatActivity activity, OnResult onresult, String medium_id, int semester_id,
                                      String standard_id) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getSubject.php?mediumId=" + medium_id + "&semesterId=" + semester_id + "&standardId=" + standard_id)
                .build();
        String query = String.valueOf(uri);
        //Log.e("getSubject", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }


    public static void getQuiz(AppCompatActivity activity, OnResult onresult, String medium_id, String semester_id,
                               String standard_id, String textbook_id) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getQuiz.php?mediumId=" + medium_id + "&semesterId=" + semester_id + "&standardId=" + standard_id +
                        "&textbookId=" + textbook_id)
                .build();
        String query = String.valueOf(uri);
        //Log.e("getSubject", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }


    public static void getSylalbusPDF(AppCompatActivity activity, OnResult onresult, String medium_id, String semester_id,
                                      String standard_no, String subject_id) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getSyllabus.php?mediumId=" + medium_id + "&semesterId=" + semester_id + "&standardId=" + standard_no +
                        "&subjectId=" + subject_id)
                .build();
        String query = String.valueOf(uri);
        //Log.e("getSubject", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getBlueprint(AppCompatActivity activity, OnResult onresult, String medium_id, String semester_id,
                                    String standard_no, String subject_id) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getBlueprint.php?mediumId=" + medium_id + "&semesterId=" + semester_id + "&standardId=" + standard_no +
                        "&subjectId=" + subject_id)
                .build();
        String query = String.valueOf(uri);
        //Log.e("getSubject", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getStudyConclusion(AppCompatActivity activity, OnResult onresult, String medium_id, String semester_id,
                                          String standard_no, String subject_id) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getStudyConclusion.php?mediumId=" + medium_id + "&semesterId=" + semester_id + "&standardId=" + standard_no +
                        "&subjectId=" + subject_id)
                .build();
        String query = String.valueOf(uri);
        //Log.e("getSubject", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getVideo(AppCompatActivity activity, OnResult onresult, String medium_id, int semester_id,
                                String standard_no, int subject_id, int chapter_id) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getVideo.php?mediumId=" + medium_id + "&semesterId=" + semester_id + "&standardId=" + standard_no +
                        "&subjectId=" + subject_id + "&chapterId=" + chapter_id)
                .build();
        String query = String.valueOf(uri);
        //Log.e("getVideo", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getEvaluation(AppCompatActivity activity, OnResult onresult, int medium_id, int semester_id,
                                     int sectionId,
                                     int standardId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getEvolution.php?mediumId=" + medium_id + "&sectionId=" + sectionId + "&semesterId=" + semester_id +
                        "&standardId=" + standardId)
                .build();
        String query = String.valueOf(uri);
        //Log.e("getEvaluation", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getTeacherAddition(AppCompatActivity activity, OnResult onresult, String medium_id, int semester_id,
                                          String standard_no, int subject_id, int chapter_id) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getTeacherEdition.php?mediumId=" + medium_id + "&semesterId=" + semester_id + "&standardId=" + standard_no +
                        "&subjectId=" + subject_id + "&chapterId=" + chapter_id)
                .build();
        String query = String.valueOf(uri);
        //Log.e("getSubject", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getBoardMembers(AppCompatActivity activity, OnResult onresult) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getBoardMembers.php")
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getPopup(AppCompatActivity activity, OnResult onresult, int mediumId, String device_model, String device_os, String device_id, String device_osversion,
                                String firebase_token) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getPopup.php?mediumId=" + mediumId + "&device_model=" + device_model + "&device_os=" + device_os + "&device_id=" + device_id +
                        "&device_osversion=" + device_osversion + "&firebase_token=" + firebase_token)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getLink(AppCompatActivity activity, OnResult onresult, int mediumId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getImpLink.php?mediumId=" + mediumId)
                .build();
        String query = String.valueOf(uri);
        //("getBoardMembers", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getSubLink(AppCompatActivity activity, OnResult onresult, int impLinkId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getSubLink.php?impLinkId=" + impLinkId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getSlider_image(AppCompatActivity activity, OnResult onresult, int mediumId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getSlider.php?mediumId=" + mediumId)
                .build();
        String query = String.valueOf(uri);
        //Log.e("getBoardMembers", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }


    public static void getSSAStaff(AppCompatActivity activity, OnResult onresult) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getSsaStaffs.php")
                .build();
        String query = String.valueOf(uri);
        //Log.e("getSsaStaffs", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getCreditors(AppCompatActivity activity, OnResult onresult) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getCreditsMembers.php")
                .build();
        String query = String.valueOf(uri);
        //Log.e("getSsaStaffs", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }


    public static void getAdministratorStaff(AppCompatActivity activity, OnResult onresult) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getAdministratorStaffs.php")
                .build();
        String query = String.valueOf(uri);
        //Log.e("getAdministratorStaffs", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getAboutUs(AppCompatActivity activity, OnResult onresult) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getAboutUs.php")
                .build();
        String query = String.valueOf(uri);
        //Log.e("getAdministratorStaffs", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getTermsCondition(AppCompatActivity activity, OnResult onresult) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getTermsCondition.php")
                .build();
        String query = String.valueOf(uri);
        //Log.e("getAdministratorStaffs", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getMyAccount(AppCompatActivity activity, OnResult onresult, int userId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getMyAccount.php?userId=" + userId)
                .build();
        String query = String.valueOf(uri);
        //Log.e("getAdministratorStaffs", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void postUploadLink(AppCompatActivity activity, OnResult onresult, int userId, String title, String youtubeLink) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("postUploadlink.php?userId=" + userId + "&title=" + title + "&youtubelink=" + youtubeLink)
                .build();
        String query = String.valueOf(uri);
        //Log.e("getAdministratorStaffs", "Calling API:" + URLDecoder.decode(query));
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
        //mysqli_set_charset($connect, 'utf8');
    }


    public static void updateProfile(AppCompatActivity activity, OnResult onresult, String name, String schoolName, String taluka,
                                     String district, String role, String userId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("updateProfile.php?name=" + name + "&schoolName=" + schoolName + "&taluka=" + taluka + "&district=" + district +
                        "&role=" + role + "&userId=" + userId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void doSignUpUser(AppCompatActivity activity, OnResult onresult, String fullname, String emailid, String password, String mobileno, String role, String schoolname, String tal, String dist, String loginwith) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("signup.php?student_fullname=" + fullname + "&student_email=" + emailid + "&student_password=" + password + "&student_mobileno=" + mobileno + "&role=" + role + "&student_school=" + schoolname + "&student_tal=" + tal + "&student_dist=" + dist + "&login_with=" + loginwith)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void doLogin(AppCompatActivity activity, OnResult onresult, String email, String password, String loginWith,
                               String device_model, String device_os, String device_id, String device_osversion,
                               String firebase_token) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("login.php?email=" + email + "&password=" + password + "&loginwith=" + loginWith +
                        "&device_model=" + device_model + "&device_os=" + device_os + "&device_id=" + device_id +
                        "&device_osversion=" + device_osversion + "&firebase_token=" + firebase_token)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getLanguage(AppCompatActivity activity, OnResult onresult) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getLanguage.php")
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getSection(AppCompatActivity activity, OnResult onresult, int mediumId, int standardId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getSection.php?mediumId=" + mediumId + "&standard=" + standardId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getExam(AppCompatActivity activity, OnResult onresult, int mediumId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getExam.php?mediumId=" + mediumId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getExamPaper(AppCompatActivity activity, OnResult onresult, int mediumId, int examId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getExamPaper.php?mediumId=" + mediumId + "&examId=" + examId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getExamStudyVideo(AppCompatActivity activity, OnResult onresult, int mediumId, int examId, int papersId, int subjectId, int chapterId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getExamStudyVideo.php?mediumId=" + mediumId + "&examId=" + examId + "&papersId=" + papersId + "&subjectId=" + subjectId + "&chapterId=" + chapterId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getMinamiVideo(AppCompatActivity activity, OnResult onresult, int mediumId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("minamiduniya.php?mediumId=" + mediumId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getGroup(AppCompatActivity activity, OnResult onresult, int userId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getGroup.php?userId=" + userId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getDonation(AppCompatActivity activity, OnResult onresult, int mediumId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getDonation.php?mediumId=" + mediumId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getExamInformation(AppCompatActivity activity, OnResult onresult, int mediumId, int examId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getExamInfo.php?mediumId=" + mediumId + "&examId=" + examId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getExamChapter(AppCompatActivity activity, OnResult onresult, int mediumId, int examId, int papersId, int subjectId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getExamChapter.php?mediumId=" + mediumId + "&examId=" + examId + "&papersId=" + papersId + "&subjectId=" + subjectId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getExamChapterOptionList(AppCompatActivity activity, OnResult onresult, int mediumId, int chapterId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getExamChapterOptionList.php?mediumId=" + mediumId + "&chapterId=" + chapterId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getExamSubjectOptionList(AppCompatActivity activity, OnResult onresult, int mediumId, int subjectId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getExamSubjectOptionList.php?mediumId=" + mediumId + "&subjectId=" + subjectId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getExamTextbook(AppCompatActivity activity, OnResult onresult, int mediumId, int examId, int papersId, int subjectId, int chapterId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getExamTextbook.php?mediumId=" + mediumId + "&examId=" + examId + "&papersId=" + papersId + "&subjectId=" + subjectId + "&chapterId=" + chapterId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getExamWorksheetChapter(AppCompatActivity activity, OnResult onresult, int mediumId, int examId, int papersId, int subjectId, int chapterId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getExamWorksheetChapter.php?mediumId=" + mediumId + "&examId=" + examId + "&papersId=" + papersId + "&subjectId=" + subjectId + "&chapterId=" + chapterId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getExamSubject(AppCompatActivity activity, OnResult onresult, int mediumId, int examId, int papersId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getExamSubject.php?mediumId=" + mediumId + "&examId=" + examId + "&papersId=" + papersId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }


    public static void getClass(AppCompatActivity activity, OnResult onresult, int mediumId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getClass.php?mediumId=" + mediumId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getSubject(AppCompatActivity activity, OnResult onresult, int mediumId, int sectionId, int standardId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getSubject.php?mediumId=" + mediumId + "&sectionId=" + sectionId + "&standardId=" + standardId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getChapter(AppCompatActivity activity, OnResult onresult, int mediumId, int sectionId,
                                  int semesterId, int standardId, int subjectId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getChapter.php?mediumId=" + mediumId + "&sectionId=" + sectionId + "&semesterId=" + semesterId +
                        "&standardId=" + standardId + "&subjectId=" + subjectId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getSubjectOptionList(AppCompatActivity activity, OnResult onresult, int mediumId, int subjectId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getSubjectOptionList.php?mediumId=" + mediumId + "&subjectId=" + subjectId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }


    public static void getPdfSubject(AppCompatActivity activity, OnResult onresult, int mediumId, int subjectId, String optionType) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getPdfSubject.php?mediumId=" + mediumId + "&subjectId=" + subjectId + "&optionType=" + optionType)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getOldPprEssay(AppCompatActivity activity, OnResult onresult, int mediumId, int subjectId, String optionType) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getOldPprEssay.php?mediumId=" + mediumId + "&subjectId=" + subjectId + "&optionType=" + optionType)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getChapterOption(AppCompatActivity activity, OnResult onresult, int mediumId, int sectionId,
                                        int semesterId, int standardId, int subjectId, int chapterId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getchapterOptionList.php?mediumId=" + mediumId + "&sectionId=" + sectionId + "&semesterId=" + semesterId +
                        "&standardId=" + standardId + "&subjectId=" + subjectId + "&chapterId=" + chapterId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }


    public static void getExamWorksheetSubject(AppCompatActivity activity, OnResult onresult, int mediumId, int examId, int papersId, int subjectId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getExamWorksheetSubject.php?mediumId=" + mediumId + "&examId=" + examId + "&papersId=" + papersId
                        + "&subjectId=" + subjectId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getExamOldPapers(AppCompatActivity activity, OnResult onresult, int mediumId, int examId, int papersId, int subjectId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getExamOldPapers.php?mediumId=" + mediumId + "&examId=" + examId + "&papersId=" + papersId
                        + "&subjectId=" + subjectId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getExamSyllabus(AppCompatActivity activity, OnResult onresult, int mediumId, int examId, int papersId, int subjectId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getExamSyllabus.php?mediumId=" + mediumId + "&examId=" + examId + "&papersId=" + papersId
                        + "&subjectId=" + subjectId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getExamOmr(AppCompatActivity activity, OnResult onresult, int mediumId, int examId, int papersId, int subjectId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getExamOmr.php?mediumId=" + mediumId + "&examId=" + examId + "&papersId=" + papersId
                        + "&subjectId=" + subjectId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getPDFChapter(AppCompatActivity activity, OnResult onresult, int mediumId, int sectionId,
                                     int semesterId, int standardId, int subjectId, int chapterId, String optionType) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getPdf.php?mediumId=" + mediumId + "&sectionId=" + sectionId + "&semesterId=" + semesterId +
                        "&standardId=" + standardId + "&subjectId=" + subjectId + "&chapterId=" + chapterId +
                        "&optionType=" + optionType)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void sendOTP(AppCompatActivity activity, OnResult onresult, int userId, String mobile) {
        //http://gujarateducation.org/android_api/sendOTP.php?mobile=9726824682&userid=1
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("sendOTP.php?userid=" + userId + "&mobile=" + mobile)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void verifyOTP(AppCompatActivity activity, OnResult onresult, int userId, String Otp, String mobile,
                                 String device_model, String device_os, String device_id, String device_osversion,
                                 String firebase_token) {
        //http://gujarateducation.org/android_api/verifyOTP.php?userId=1&otp=452192&mobile=9726824682
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("verifyOTP.php?userId=" + userId + "&otp=" + Otp + "&mobile=" + mobile +
                        "&device_model=" + device_model + "&device_os=" + device_os + "&device_id=" + device_id +
                        "&device_osversion=" + device_osversion + "&firebase_token=" + firebase_token)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getStudyVideo(AppCompatActivity activity, OnResult onresult, int mediumId, int sectionId,
                                     int semesterId, int standardId, int subjectId, int chapterId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getStudyVideo.php?mediumId=" + mediumId + "&sectionId=" + sectionId + "&semesterId=" + semesterId +
                        "&standardId=" + standardId + "&subjectId=" + subjectId + "&chapterId=" + chapterId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getChapterWorksheet(AppCompatActivity activity, OnResult onresult, int mediumId, int sectionId,
                                           int semesterId, int standardId, int subjectId, int chapterId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getChapterWorksheet.php?mediumId=" + mediumId + "&sectionId=" + sectionId + "&semesterId=" + semesterId +
                        "&standardId=" + standardId + "&subjectId=" + subjectId + "&chapterId=" + chapterId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getTeachersEdition(AppCompatActivity activity, OnResult onresult, int mediumId, int sectionId,
                                          int semesterId, int standardId, int subjectId, int chapterId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getTeachersEdition.php?mediumId=" + mediumId + "&sectionId=" + sectionId + "&semesterId=" + semesterId +
                        "&standardId=" + standardId + "&subjectId=" + subjectId + "&chapterId=" + chapterId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getQuizCompExam(AppCompatActivity activity, OnResult onresult, int mediumId, int examId,
                                       int papersId, int subjectId, int chapterId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getQuizCompExam.php?mediumId=" + mediumId + "&examId=" + examId + "&papersId=" + papersId +
                        "&subjectId=" + subjectId + "&chapterId=" + chapterId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getMagazineEdition(AppCompatActivity activity, OnResult onresult, int mediumId, int magazineId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getMagazineList.php?mediumId=" + mediumId + "&magazineId=" + magazineId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getCalenderEdition(AppCompatActivity activity, OnResult onresult, int calendarId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getCalendarEdition.php?calendarId=" + calendarId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getDaySpecial(AppCompatActivity activity, OnResult onresult, int mediumId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getDaySpecial.php?mediumId=" + mediumId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getMagazine(AppCompatActivity activity, OnResult onresult, int mediumId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getMagazine.php?mediumId=" + mediumId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getCalender(AppCompatActivity activity, OnResult onresult, int mediumId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getCalendar.php?mediumId=" + mediumId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getEducationCornerList(AppCompatActivity activity, OnResult onresult) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getEducationCornerList.php")
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }//http://gujarateducation.org/android_api/getEducationCornerList.php?userId=1


    public static void getNotificationList(AppCompatActivity activity, OnResult onresult, String role) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getNotificationList.php?role=" + role)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    public static void getNewsCircular(AppCompatActivity activity, OnResult onresult, int mediumId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getNewsCircular.php?mediumId=" + mediumId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }


    public static void getPreprimaryTextbook(AppCompatActivity activity, OnResult onresult, int mediumId, int semesterId,
                                             int sectionId, int standardId, int subjectId, int chapterId) {
        Uri uri = new Uri.Builder().scheme("http")
                .authority(Connection.SERVER_GETURL)
                .path("getPreprimaryTextbook.php?mediumId=" + mediumId
                        + "&semesterId=" + semesterId + "&sectionId=" + sectionId + "&standardId=" + standardId +
                        "&subjectId=" + subjectId +
                        "&chapterId=" + chapterId)
                .build();
        String query = String.valueOf(uri);
        APIs.callAPI(activity, onresult, URLDecoder.decode(query));
    }

    //http://ssamsbsurat.org/android_api/getSubject.php?mediumId=2

    //http://ssamsbsurat.org/android_api/getAllTextbook.php?mediumId=2&standardId=6&subjectId=10
    //http://ssamsbsurat.org/android_api/udateProfile.php?student_id=1&student_mobileno=8889997744
}
