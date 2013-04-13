package olutopas;

import com.avaje.ebean.EbeanServer;
import java.util.List;
import java.util.Scanner;
import javax.persistence.OptimisticLockException;
import olutopas.model.Beer;
import olutopas.model.Brewery;
import olutopas.model.Rating;
import olutopas.model.User;

public class Application {

    private EbeanServer server;
    private Scanner scanner = new Scanner(System.in);
    User userLogged = new User();
    
    public Application(EbeanServer server) {
        this.server = server;
    }

    public void run(boolean newDatabase) {
        if (newDatabase) {
            seedDatabase();
        }

        System.out.println("Welcome!");

        int k=0;
        while (true) {    
            if(k==0) {
                userLogged=login();
                ++k;
            }
            menu();
            System.out.print("> ");
            String command = scanner.nextLine();

            if (command.equals("0")) {
                break;
            } else if (command.equals("1")) {
                findBrewery();
            } else if (command.equals("2")) {
                findBeer(userLogged);
            } else if (command.equals("3")) {
                addBeer();
            } else if (command.equals("4")) {
                listBreweries();
            } else if (command.equals("5")) {
                deleteBeer();
            } else if (command.equals("6")) {
                listBeers();
            } else if (command.equals("7")) {
                addBrewery();
            } else if (command.equals("8")) {
                deleteBrewery();
            } else if (command.equals("9")) {
                listUsers();
            } else if (command.equals("t")) {
                listRatings();
            } else {
                System.out.println("unknown command");
            }

            System.out.print("\npress enter to continue");
            scanner.nextLine();
        }

        System.out.println("bye");
    }

    private void menu() {
        System.out.println("");
        System.out.println("1   find brewery");
        System.out.println("2   find/rate beer");
        System.out.println("3   add beer");
        System.out.println("4   list breweries");
        System.out.println("5   delete beer");
        System.out.println("6   list beers");
        System.out.println("7   add brewery");
        System.out.println("8   delete brewery");
        System.out.println("9   list users");
        System.out.println("t   list ratings");
        System.out.println("0   quit");
        System.out.println("");
    }

    // jos kanta on luotu uudelleen, suoritetaan tämä ja laitetaan kantaan hiukan dataa
    private void seedDatabase() throws OptimisticLockException {
        Brewery brewery = new Brewery("Schlenkerla");
        brewery.addBeer(new Beer("Urbock"));
        brewery.addBeer(new Beer("Lager"));
        // tallettaa myös luodut oluet, sillä Brewery:n OneToMany-mappingiin on määritelty
        // CascadeType.all
        server.save(brewery);

        // luodaan olut ilman panimon asettamista
        Beer b = new Beer("Märzen");
        server.save(b);
        
        // jotta saamme panimon asetettua, tulee olot lukea uudelleen kannasta
        b = server.find(Beer.class, b.getId());        
        brewery = server.find(Brewery.class, brewery.getId());        
        brewery.addBeer(b);
        server.save(brewery);
        
        server.save(new Brewery("Paulaner"));
    }

    private void findBeer(User userLogged) {
        System.out.print("beer to find: ");
        String name= scanner.nextLine();
        Beer foundBeer = server.find(Beer.class).where().like("name", name).findUnique();

        if (foundBeer == null) {
            System.out.println(name + " not found");
            return;
        }
//        Rating ratingki = server.find(Rating.class).where().like("beer_id", foundBeer.getId()+"" ).findUnique();
        System.out.println("found: " + foundBeer);   
        List<Rating> ratings = server.find(Rating.class)
                .findList();
        int summa=0;
        for (Rating rating : ratings) {
            summa=+rating.getValue();
        }
        
        if(!ratings.isEmpty()) {
            double average=summa/ratings.size();
            System.out.println("number of ratings "+ratings.size()+" average "+average);
        }
        else {
            System.out.println("not available currently");
        }       
        
        rateBeer(foundBeer, userLogged);
    }

    private void findBrewery() {
        System.out.print("brewery to find: ");
        String n = scanner.nextLine();
        Brewery foundBrewery = server.find(Brewery.class).where().like("name", n).findUnique();

        if (foundBrewery == null) {
            System.out.println(n + " not found");
            return;
        }

        System.out.println(foundBrewery);
        for (Beer bier : foundBrewery.getBeers()) {
            System.out.println("   " + bier.getName());
        }
    }

    private void listBreweries() {
        List<Brewery> breweries = server.find(Brewery.class).findList();
        for (Brewery brewery : breweries) {
            System.out.println(brewery);
        }
    }

    private void addBeer() {
        System.out.print("to which brewery: ");
        String name = scanner.nextLine();
        Brewery brewery = server.find(Brewery.class).where().like("name", name).findUnique();

        if (brewery == null) {
            System.out.println(name + " does not exist");
            return;
        }

        System.out.print("beer to add: ");

        name = scanner.nextLine();

        Beer exists = server.find(Beer.class).where().like("name", name).findUnique();
        if (exists != null) {
            System.out.println(name + " exists already");
            return;
        }

        brewery.addBeer(new Beer(name));
        server.save(brewery);
        System.out.println(name + " added to " + brewery.getName());
    }

    private void deleteBeer() {
        System.out.print("beer to delete: ");
        String n = scanner.nextLine();
        Beer beerToDelete = server.find(Beer.class).where().like("name", n).findUnique();

        if (beerToDelete == null) {
            System.out.println(n + " not found");
            return;
        }

        server.delete(beerToDelete);
        System.out.println("deleted: " + beerToDelete);

    }
    
    private void listBeers() {
        List<Beer> beers = server.find(Beer.class)
                .orderBy("brewery")
                .findList();
        for (Beer beer : beers) {
            System.out.println(beer);
        }
    }
    
    private void addBrewery() {
       
        System.out.print("brewery to add: ");

        String name = scanner.nextLine();

        Brewery exists = server.find(Brewery.class).where().like("name", name).findUnique();
        if (exists != null) {
            System.out.println(name + " exists already");
            return;
        }
        Brewery brewery = new Brewery();
        brewery.setName(name);
        server.save(brewery);
        System.out.println(name + " added to " + brewery.getName());
    }
    private void deleteBrewery() {
        System.out.print("brewery to delete: ");
        String name = scanner.nextLine();
        Brewery breweryToDelete = server.find(Brewery.class).where().like("name", name).findUnique();

        if (breweryToDelete == null) {
            System.out.println(name + " not found");
            return;
        }

        server.delete(breweryToDelete);
        System.out.println("deleted: " + breweryToDelete);
    }
    private void registerNewUser() {
        System.out.println("Register a new user");
        System.out.print("give username: ");
        String kayttajatunnus = scanner.nextLine();

        User exists = server.find(User.class).where().like("kayttajatunnus", kayttajatunnus).findUnique();
        if (exists != null) {
            System.out.println(kayttajatunnus + " exists already");
            return;
        }
        User user = new User();
        user.setKayttajatunnus(kayttajatunnus);
        server.save(user);
        System.out.println("user created!");
    }
    
    private User login() {
        String kayttajatunnus;
        do {
        System.out.println("Login (give ? to register a new user)");  
        System.out.print("username: ");
        kayttajatunnus = scanner.nextLine();
        if(kayttajatunnus.equals("?")) {
            registerNewUser();
        }
        } while(kayttajatunnus.equals("?"));
        
        User exists = server.find(User.class).where().like("kayttajatunnus", kayttajatunnus).findUnique();
        if(exists != null) {
            System.out.println("Welcome to Ratebeer! "+kayttajatunnus);
            return exists;
        }    
        if(exists == null) {
            System.out.println("Username doesn't exist");
            System.exit(0);
        }
        return null;
   }
   private void listUsers() {
        List<User> users = server.find(User.class)
                .findList();
        for (User user : users) {
            System.out.println(user);
        }
   } 
   private void listRatings() {
        System.out.println("Ratings by "+userLogged);
        List<Rating> ratings = server.find(Rating.class)
                .findList();
        for (Rating rating : ratings) {
            System.out.println(rating);
        }
   }
   private void rateBeer(Beer beer, User userLogged) {
       System.out.print("Give rating (leave empty if not): ");
       int value = scanner.nextInt();
//       User user = server.find(User.class).where().like("kayttajatunnus", user).findUnique();
       Rating rating = new Rating(beer,userLogged,value);
       server.save(rating);
   } 
}
