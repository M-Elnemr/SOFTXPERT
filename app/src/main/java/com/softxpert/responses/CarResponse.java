
package com.softxpert.responses;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CarResponse {

    @SerializedName("data")
    private List<Data> mData;
    @SerializedName("status")
    private Long mStatus;

    public List<Data> getData() {
        return mData;
    }

    public void setData(List<Data> data) {
        mData = data;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

    public class Data {

        @SerializedName("brand")
        private String mBrand;
        @SerializedName("constructionYear")
        private String mConstractionYear;
        @SerializedName("id")
        private Long mId;
        @SerializedName("imageUrl")
        private String mImageUrl;
        @SerializedName("isUsed")
        private Boolean mIsUsed;

        public String getBrand() {
            return mBrand;
        }

        public void setBrand(String brand) {
            mBrand = brand;
        }

        public String getConstractionYear() {
            return mConstractionYear;
        }

        public void setConstractionYear(String constractionYear) {
            mConstractionYear = constractionYear;
        }

        public Long getId() {
            return mId;
        }

        public void setId(Long id) {
            mId = id;
        }

        public String getImageUrl() {
            return mImageUrl;
        }

        public void setImageUrl(String imageUrl) {
            mImageUrl = imageUrl;
        }

        public Boolean getIsUsed() {
            return mIsUsed;
        }

        public void setIsUsed(Boolean isUsed) {
            mIsUsed = isUsed;
        }

    }

}
