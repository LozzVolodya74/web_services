package com.nix.lopachak.controller;

import com.nix.lopachak.dto.CreatePersonDto;
import com.nix.lopachak.dto.PersonDto;
import com.nix.lopachak.dto.UpdatePersonDto;
import com.nix.lopachak.entity.Person;
import com.nix.lopachak.model.UserFormParams;
import com.nix.lopachak.service.PersonService;
import com.nix.lopachak.service.XssFixer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.nix.lopachak.constraints.FieldMatchConstraintValidator.addFieldErrorForFieldMatchConstraint;

/**
 * Класс - контроллер содержит методы для обработки url-admin
 *
 * @author Volodymyr Lopachak
 * @version 1.0
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PersonService personService;

    private final XssFixer fixer;

    @Autowired
    public AdminController(final PersonService personService, final XssFixer fixer) {
        this.personService = personService;
        this.fixer = fixer;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        // Редактор свойств, который обрезает строки.
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        // для привязки данных из параметров веб-запроса к объектам
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @RequestMapping(value = "/refresh")
    public String refresh(Model model) {
        model.addAttribute("name", getCurrentUsername());
        model.addAttribute("persons", personService.findAll());
        return "admin-main";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView create(Model model) {
        return gotoUserForm(model, new CreatePersonDto(), true);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView createUser(@Valid @ModelAttribute("user") CreatePersonDto dto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            addFieldErrorForFieldMatchConstraint(bindingResult);
            return gotoUserForm(model, dto, true);
        }
        personService.create(dto);
        return new ModelAndView("redirect:/admin/refresh");
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ModelAndView update(@RequestParam("up_id") long up_id, Model model) {
        Person personUpdate = personService.findById(up_id);
        return gotoUserForm(model, UpdatePersonDto.personToDto(personUpdate), false);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@Valid @ModelAttribute("user") UpdatePersonDto dto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            addFieldErrorForFieldMatchConstraint(bindingResult);
            return gotoUserForm(model, dto, false);
        }
        Person updatePerson = dto.toPerson();
        personService.update(updatePerson);
        return new ModelAndView("redirect:/admin/refresh");
    }

    @RequestMapping(value = "/delete")
    public String delete(HttpServletRequest req) {
        long id = Long.parseLong(req.getParameter("id"));
        personService.remove(personService.findById(id));
        return "redirect:/admin/refresh";
    }

    /**
     * Метод для перехода на соответствующее представление
     *
     * @param model - Model
     * @param dto - форма для создания нового пользователя
     * @param create - флаг что нужно создать пользователя
     * @return
     */
    private ModelAndView gotoUserForm(Model model, PersonDto dto, boolean create) {
        String message;
        String title;
        String submitAction;
        if (create) {
            message = "CREATE NEW USER";
            title = "New User Creation Form";
            submitAction = "/admin/add";
        } else {
            message = "UPDATE USER";
            title = "User Update Form";
            submitAction = "/admin/update";
        }
        model.addAttribute("name", getCurrentUsername());
        model.addAttribute("message", message);
        model.addAttribute(
                "params",
                new UserFormParams(title, submitAction, "/admin/refresh", false, true, create)
        );
        return new ModelAndView("user-form", "user", dto);
    }

    /**
     * Метод возвращоет имя авторизированного пользователя
     * @return - имя администратора
     */
    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
