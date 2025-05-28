package net.tp3.tp_33.web;

import jakarta.validation.Valid;

import net.tp3.tp_33.entite.Patient;
import net.tp3.tp_33.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/index")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "0") int page,
                        @RequestParam(name="size",defaultValue = "5") int size,
                        @RequestParam(name = "key",defaultValue ="") String key){
        Page<Patient> patients =  patientRepository.findByFirstNameContainingIgnoreCase(key,PageRequest.of(page, size));
        model.addAttribute("Listpatients", patients.getContent());
        model.addAttribute("pages", new int[patients.getTotalPages()]);
        model.addAttribute("pageCourent",page);
        model.addAttribute("key",key);

        return "patients";
    }
    @GetMapping("deletePatient")
    public String deletePatient( @RequestParam(name = "id") Long id,@RequestParam(name = "key") String key,@RequestParam(name = "page") int page){
        patientRepository.deleteById(id);
        return "redirect:/index?key="+key+"&page="+page;

    }
    @GetMapping("/edit")
    public String edit(Model model,@RequestParam(name="id") Long id,@RequestParam(name = "key",defaultValue = "") String key,@RequestParam(name = "page",defaultValue = "0")int page){
        Optional<Patient> p= patientRepository.findById(id);
        model.addAttribute("patient",p);
        model.addAttribute("key",key);
        model.addAttribute("page",page);

        return "editPatient";

    }
    @GetMapping("/formPatients")
    public String formPatient(Model model){
        model.addAttribute("patient", new Patient());
        return "formPatient";
    }
    @PostMapping("/savePatient")

    public String SavePatient(Model model, @Valid @ModelAttribute("patient") Patient patient, BindingResult bindingResult,
                              @RequestParam(name = "key",defaultValue = "") String Key,
                              @RequestParam(name = "page",defaultValue = "0") int page) {
        if(bindingResult.hasErrors()){ return "formPatient"; }
        System.out.println("pation ===> "+patient);
        patientRepository.save(patient);
        return "redirect:/index?key="+Key+"&page="+page;
    }
    @GetMapping("/")
    public String home(){
        return "redirect:/index";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
