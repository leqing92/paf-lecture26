package sg.edu.nus.iss.lecture26;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sg.edu.nus.iss.lecture26.repository.TVShowRepository;

@SpringBootApplication
public class Lecture26Application implements CommandLineRunner {

	@Autowired
	TVShowRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(Lecture26Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		for(Document doc : repo.findShowByName("a")){
			String name = doc.getString("name");
			List <String> genres = doc.getList("genres", String.class);
			// System.out.printf("name: %s, genres: %s\n", name, genres.toString());

			System.out.printf(">>>docs: %s \n" , doc.toString());
			
		}

		System.out.printf("tvshow count by language: %d \n", repo.countShowsByLanguage("English"));

		System.out.printf("Type of show with rating gte 7: %s\n", repo.getTypesByRating(7));
		System.exit(0);
	}

}
