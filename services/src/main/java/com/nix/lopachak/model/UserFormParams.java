package com.nix.lopachak.model;

/**
 * Class -форма для хранения параметров User-формы
 *
 * @author Volodymyr Lopachak
 * @version 1.0
 */
public class UserFormParams {

    private final String title;

    private final String submitAction;

    private final String cancelAction;

    private final boolean withCaptcha;

    private final boolean withLogout;

    private final boolean loginEnabled;

    public UserFormParams(final String title,
                          final String submitAction,
                          final String cancelAction,
                          final boolean withCaptcha,
                          final boolean withLogout,
                          final boolean loginEnabled) {
        this.title = title;
        this.submitAction = submitAction;
        this.cancelAction = cancelAction;
        this.withCaptcha = withCaptcha;
        this.withLogout = withLogout;
        this.loginEnabled = loginEnabled;
    }

    public String getTitle() {
        return title;
    }

    public String getSubmitAction() {
        return submitAction;
    }

    public String getCancelAction() {
        return cancelAction;
    }

    public boolean isWithCaptcha() {
        return withCaptcha;
    }

    public boolean isWithLogout() {
        return withLogout;
    }

    public boolean isLoginEnabled() {
        return loginEnabled;
    }
}
