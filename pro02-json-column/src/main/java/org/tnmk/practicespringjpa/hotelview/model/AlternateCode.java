package org.tnmk.practicespringjpa.hotelview.model;

/**
 * This represents alternate codes that can be sent in a hotel file.
 * For example, Hilton may send us the Marriott code for a hotel.
 *
 * Created by mick on 07/05/18.
 */
public class AlternateCode {
    private String code;
    private String accountUuid;

    public AlternateCode(String code, String accountUuid) {
        this.code = code;
        this.accountUuid = accountUuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAccountUuid() {
        return accountUuid;
    }

    public void setAccountUuid(String accountUuid) {
        this.accountUuid = accountUuid;
    }
}
