package com.nix.lopachak.controller;

import cn.apiclub.captcha.Captcha;
import com.nix.lopachak.dto.RegisterPersonDto;
import com.nix.lopachak.entity.Person;
import com.nix.lopachak.model.UserFormParams;
import com.nix.lopachak.service.PersonService;
import com.nix.lopachak.service.XssFixer;
import com.nix.lopachak.util.CaptchaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.nix.lopachak.Constants.ADMIN;
import static com.nix.lopachak.Constants.USER;
import static com.nix.lopachak.constraints.FieldMatchConstraintValidator.addFieldErrorForFieldMatchConstraint;
import static com.nix.lopachak.util.SecurityUtils.*;
import static java.util.Objects.requireNonNull;

/**
 * Класс - контроллер для обработи общих URL-запросов
 *
 * @author Volodymyr Lopachak
 * @version 1.0
 */
@Controller
public class PublicDataController {

    private final PersonService personService;

    private final XssFixer fixer;

    @Autowired
    public PublicDataController(PersonService personService, XssFixer fixer) {
        this.personService = personService;
        this.fixer = fixer;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    /**
     * Точка входа в приложение
     *
     * @return переход на user-login.jsp если пользователь не залогинен или на страницу пользователя
     */
    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String login() {
        if (isLoggedIn()) {
            return redirectToHomePage();
        }
        return "user-login";
    }

    /**
     * Метод для перехода на домашнюю странице пользователя
     *
     * @return - страницу, куда необходимо перейти в зависимости от роли пользователя
     */
    @RequestMapping(value = "/common/my-account", method = RequestMethod.GET)
    public String redirectToHomePage() {
        UserDetails userDetails = requireNonNull(getCurrentUserDetails(), "Current user details must be set!");
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            if (ADMIN.equals(authority.getAuthority())) {
                return "redirect:/admin/refresh";
            } else if (USER.equals(authority.getAuthority())) {
                return "redirect:/user/home";
            } else {
                throw new UnsupportedOperationException("Unsupported authority: " + authority.getAuthority());
            }
        }
        throw new IllegalStateException("Current user details does not contain any authorities");
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register(HttpServletRequest request, HttpSession session) {
        if (isLoggedIn()) {
            return new ModelAndView(redirectToHomePage());
        }
        RegisterPersonDto dto = new RegisterPersonDto();
        return gotoRegistrationUserForm(request, session, dto);
    }

    /**
     * Метод для переходя на форму регистрации пользователя
     *
     * @param request - HttpServletRequest
     * @param session - HttpSession
     * @param dto     - форма для создания нового пользователя
     * @return - модель и представление куда необходимо перейти
     */
    private ModelAndView gotoRegistrationUserForm(HttpServletRequest request,
                                                  HttpSession session,
                                                  RegisterPersonDto dto) {
        getCaptcha(dto, session);
        // устанавливаем параметры в User-форму
        request.setAttribute(
                "params",
                new UserFormParams(
                        "User Registration Form",
                        "/register",
                        "/",
                        true,
                        false,
                        true
                )
        );
        return new ModelAndView("user-form", "user", dto);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@Valid @ModelAttribute("user") RegisterPersonDto dto,
                                 BindingResult bindingResult, HttpServletRequest request, HttpSession session) {
        if (isLoggedIn()) {
            return new ModelAndView(redirectToHomePage());
        }
        if (bindingResult.hasErrors()) {
            addFieldErrorForFieldMatchConstraint(bindingResult);
            return gotoRegistrationUserForm(request, session, dto);
        }
        Captcha captcha = (Captcha) session.getAttribute("CAPTCHA");
        if (captcha == null) {
            bindingResult.addError(createCaptchaError("Captcha not defined"));
            return gotoRegistrationUserForm(request, session, dto);
        } else if (!captcha.getAnswer().equals(dto.getCaptchaText())) {
            bindingResult.addError(createCaptchaError("Captcha is invalid"));
            return gotoRegistrationUserForm(request, session, dto);
        }
        Person createPerson = personService.create(dto);
        authenticate(createPerson);
        session.removeAttribute("CAPTCHA");
        return new ModelAndView(redirectToHomePage());
    }

    /**
     *
     * Метод для установки сообщения об ошибке Captcha
     *
     * @param message - сообщение об ошибке Captcha
     * @return - объект класса FieldError
     */
    private ObjectError createCaptchaError(String message) {
        return new FieldError("user", "captchaText", message);
    }

    /**
     * Метод добавляет параметер captcha в сессию
     *
     * @param dto     - Class - форма для создания нового пользователя
     * @param session - текущая сессия
     */
    private void getCaptcha(RegisterPersonDto dto, HttpSession session) {
        Captcha captcha = CaptchaUtils.createCaptcha(240, 70);
        session.setAttribute("CAPTCHA", captcha);
        dto.setCaptchaText("");
        dto.setCaptchaImg(CaptchaUtils.encodeCaptcha(captcha));
    }
}
