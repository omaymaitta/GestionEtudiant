package ma.enset.gestionetudiant.web;

import lombok.AllArgsConstructor;
import ma.enset.gestionetudiant.entities.Etudiant;
import ma.enset.gestionetudiant.repositories.EtudiantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class EtudiantController {
    private EtudiantRepository etudiantRepository;
    @GetMapping(path = "/user/index")
    public String etudiants(Model model, @RequestParam(name="page",defaultValue = "0") int page, @RequestParam(name="size",defaultValue = "5") int size, @RequestParam(name="keyword",defaultValue = "") String keyword){
        Page<Etudiant> pageEtudiants=etudiantRepository.findByNomContains(keyword, PageRequest.of(page,size));
        model.addAttribute("listEtudiants",pageEtudiants.getContent());
        model.addAttribute("pages",new int[pageEtudiants.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);

        return "etudiants";
    }
    @GetMapping ("/")
    public String home() {
        return "home";
    }
    @GetMapping  ("/admin/delete")
    public String delete(Long id,String keyword,int page) {
        etudiantRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping ("/user/etudiants")
    @ResponseBody
    public List<Etudiant> listEtudiant (){
        return etudiantRepository.findAll();
    }

    @GetMapping("/admin/formEtudiants")
    public String formEtudiants(Model model){
        model.addAttribute("etudiant",new Etudiant());
        return "formEtudiants";
    }

    @GetMapping  ("/admin/editEtudiant")
    public String editEtudiant(Model model,Long id,String keyword,int page) {
        Etudiant etudiant=etudiantRepository.findById(id).orElse(null);
        if(etudiant==null) throw new RuntimeException("Etudiant introuvable");
        model.addAttribute( "etudiant", etudiant);
        model.addAttribute("page",page);
        model.addAttribute("keyword",keyword);
        return "editEtudiant";
    }

    @PostMapping(path = "/admin/save")
    public String save(Model model, @Validated Etudiant etudiant, BindingResult bindingResult, @RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "") String keyword){
        if(bindingResult.hasErrors()) return "formEtudiants";
        etudiantRepository.save(etudiant);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }
}
