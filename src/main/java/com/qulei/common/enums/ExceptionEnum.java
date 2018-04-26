package com.qulei.common.enums;


public enum ExceptionEnum {

    LOGIN_ERROR("Login failed"),
    EMPTY_ERROR("Please complete the information"), SIGNUP_ERROR("Sign up failed"), CONTENT_ERROR("Please enter the correct format username"), TITLE_EMPTY_ERROR("Please enter the title"), CONTENT_EMPTY_ERROR("Please enter the content"), COLLECTION_EMPTY_ERROR("Please select collection"), SAVE_ERROR("Save failed"), ARTICLE_OPEN_ERROR("Read failed"), ARTICLE_EDIT_ERROR("Edit failed"), ACTION_ERROR("Abnormal operation"), COLLECTION_ADD_ERROR("Add collection failed"), COLLECTION_UPDATE_ERROR("Rename collection failed"), DELETE_ERROR("delete failed"), KEYWORD_EMPTY_ERROR("Please enter the keyword"), TITLE_TOO_LONG_ERROR("the title is too long"), LOGOUT_ERROR("Log out error"), CODE_ERROR("Fail to send verification code"), CODE_NOT_RIGHT("The verification code is not right"), HAVE_SIGNUP("This phone or email has been signed up");

    private String msg;

    public String getMsg() {
        return msg;
    }


    ExceptionEnum( String message){
        this.msg = message;
    }
}
