package ma.enset.gestionetudiant;

import ma.enset.gestionetudiant.entities.Etudiant;
import ma.enset.gestionetudiant.entities.Genre;
import ma.enset.gestionetudiant.repositories.EtudiantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class GestionEtudiantApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionEtudiantApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner (EtudiantRepository etudiantRepository ) {
		return args -> {
			etudiantRepository.save(new Etudiant(null,"Omayma","Ittaqi","omayma@gmail.com",new Date(),Genre.FEMININ,false));
			etudiantRepository.save(new Etudiant(null,"Mohamed","Agherai","moha@gmail.com",new Date(),Genre.MASCULIN,true));
			etudiantRepository.save(new Etudiant(null,"Khalid","Sahl","khalid@gmail.com",new Date(),Genre.MASCULIN,false));
			etudiantRepository.save(new Etudiant(null,"Hanan","Berradi","hanan@gmail.com",new Date(),Genre.FEMININ,true));
			etudiantRepository.findAll().forEach(p->{
				System.out.println(p.getNom());
			});



		};
	}
}
