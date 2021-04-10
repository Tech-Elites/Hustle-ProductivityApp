package com.example.productivity;

import java.util.HashMap;

public class userinfo {
    String name,userid;
    userinfo()
    {}

    public void setName(String name) {
        this.name = name;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public String getUserid() {
        return userid;
    }
    public HashMap<String,Object> getDetails(String name,String userid)
    {
        HashMap<String,Object> details=new HashMap<>();
        details.put("name",name);
        details.put("userid",userid);
        return  details;
    }

}
