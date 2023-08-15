package guru.springframework.spring6webapp.bootstrap;

import guru.springframework.spring6webapp.domain.Author;
import guru.springframework.spring6webapp.domain.Book;
import guru.springframework.spring6webapp.domain.Publisher;
import guru.springframework.spring6webapp.repositories.AuthorRepository;
import guru.springframework.spring6webapp.repositories.BookRepository;
import guru.springframework.spring6webapp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Component annotation declares this as a Spring component
 * Spring creates a Spring bean for it
 * Spring Data JPA is giving the implementation for the repos
 * H2 memory database sets the ID to the saved entities
 */
@Component
public class BootstrapData implements CommandLineRunner {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    //Spring starts -> wires the repositories to the implementations provided by Spring Data JPA.
    public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        addPublisher();
        Author julesVerne = new Author();
        julesVerne.setFirstName("Jules");
        julesVerne.setLastName("Verne");
        Author julesVerneSaved = authorRepository.save(julesVerne);

        Book misteryIslandBook = new Book();
        misteryIslandBook.setTitle("Mistery Island");
        misteryIslandBook.setIsbn("123456");
        Book misteryIslandBookSaved = bookRepository.save(misteryIslandBook);

        Author steinbeck = new Author();
        steinbeck.setFirstName("Steinbeck");
        steinbeck.setLastName("John");
        Author steinbeckSaved = authorRepository.save(steinbeck);

        Book westOfEden = new Book();
        westOfEden.setTitle("West Of Eden");
        westOfEden.setIsbn("123123");
        Book westOfEdenSaved = bookRepository.save(westOfEden);

        julesVerneSaved.getBooks().add(misteryIslandBookSaved);
        steinbeckSaved.getBooks().add(westOfEdenSaved);

        authorRepository.save(steinbeckSaved);
        authorRepository.save(julesVerneSaved);

        Publisher publisher = new Publisher();
        publisher.setPublisherName("Popescu Vlad");
        publisher.setCity("Pitest");
        publisher.setState("Arges");
        publisher.setAddress("Gavenii 41");
        publisherRepository.save(publisher);

        misteryIslandBookSaved.setPublisher(publisher);
        westOfEdenSaved.setPublisher(publisher);

        bookRepository.save(misteryIslandBookSaved);
        bookRepository.save(westOfEdenSaved);

        System.out.println("In bootstrap");
        System.out.println("Author count:" + authorRepository.count());
        System.out.println("Books count:" + bookRepository.count());
        System.out.println("Publisher count: " + publisherRepository.count());
    }

    private void addPublisher() {

    }
}
