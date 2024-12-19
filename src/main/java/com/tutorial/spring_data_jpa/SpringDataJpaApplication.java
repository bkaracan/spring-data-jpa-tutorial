package com.tutorial.spring_data_jpa;

import com.tutorial.spring_data_jpa.entity.Author;
import com.tutorial.spring_data_jpa.entity.Book;
import com.tutorial.spring_data_jpa.repository.AuthorRepository;
import com.tutorial.spring_data_jpa.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class SpringDataJpaApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringDataJpaApplication.class, args);
  }

  @Bean
  public CommandLineRunner runner(AuthorRepository authorRepository, BookRepository bookRepository) {
    return args -> {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

      List<Author> authors = null;
      try {
        authors = Arrays.asList(
                new Author(
                        "Sigmund",
                        "Freud",
                        dateFormat.parse("1856-05-06"),
                        dateFormat.parse("1939-09-23"),
                        "Sigmund Freud, an Austrian neurologist and the founder of psychoanalysis,\n"
                                + "is renowned for his groundbreaking theories on the unconscious mind,\n"
                                + "defense mechanisms, and the interpretation of dreams. His work revolutionized\n"
                                + "the understanding of human behavior and continues to influence psychology\n"
                                + "and culture. Freud's key concepts include the id, ego, and superego;\n"
                                + "the Oedipus complex; and the use of free association and dream analysis as therapeutic techniques."),
                new Author(
                        "Carl",
                        "Jung",
                        dateFormat.parse("1875-07-26"),
                        dateFormat.parse("1961-06-06"),
                        "Carl Jung, a Swiss psychiatrist and psychoanalyst who founded analytical psychology,\n"
                                + "is known for his concepts of the collective unconscious, archetypes, and individuation.\n"
                                + "He expanded on Freud's theories, emphasizing the importance of spirituality\n"
                                + "and mythology in understanding the psyche. Jung developed ideas like introversion\n"
                                + "and extraversion and created the Myers-Briggs Type Indicator (MBTI) based on his theories.\n"
                                + "His contributions are essential in understanding psychological types and the process of self-realization."),
                new Author(
                        "Viktor",
                        "Frankl",
                        dateFormat.parse("1905-03-26"),
                        dateFormat.parse("1997-09-02"),
                        "Viktor Frankl, an Austrian neurologist and psychiatrist, as well as a Holocaust survivor,\n"
                                + "is best known for developing logotherapy, a form of existential analysis focused on the 'will to meaning.'\n"
                                + "His experiences in concentration camps deeply influenced his philosophy, highlighting the importance of\n"
                                + "finding purpose in suffering. Frankl's book, 'Man's Search for Meaning,' is a testament to\n"
                                + "his belief in the human capacity for resilience and the importance of meaning in life."),
                new Author(
                        "Abraham",
                        "Maslow",
                        dateFormat.parse("1908-04-01"),
                        dateFormat.parse("1970-06-08"),
                        "Abraham Maslow, an American psychologist, is famous for creating Maslow's hierarchy of needs,\n"
                                + "which outlines a pyramid of human motivations ranging from basic physiological needs to self-actualization.\n"
                                + "His humanistic psychology emphasized the positive potential of individuals and the importance of\n"
                                + "personal growth. Maslow’s work laid the foundation for humanistic psychology and continues to\n"
                                + "impact the fields of psychology, education, and management."),
                new Author(
                        "Albert",
                        "Ellis",
                        dateFormat.parse("1913-09-27"),
                        dateFormat.parse("2007-07-24"),
                        "Albert Ellis, an American psychologist, developed Rational Emotive Behavior Therapy (REBT),\n"
                                + "a form of cognitive-behavioral therapy. He emphasized the role of irrational beliefs in causing\n"
                                + "emotional distress and advocated for changing these beliefs to improve mental health. Ellis's\n"
                                + "confrontational and direct approach in therapy made him a pioneer in the field of cognitive-behavioral\n"
                                + "therapies and has influenced many contemporary approaches to counseling and psychotherapy."),
                new Author(
                        "Aaron",
                        "Beck",
                        dateFormat.parse("1921-07-18"),
                        dateFormat.parse("2021-11-01"),
                        "Aaron Beck, an American psychiatrist, is considered the father of cognitive therapy.\n"
                                + "He developed a therapy that focuses on identifying and changing negative thought patterns to improve\n"
                                + "mental health. Beck's Cognitive Behavioral Therapy (CBT) is widely used for treating depression,\n"
                                + "anxiety, and other mental disorders. His work has been instrumental in revolutionizing psychotherapy\n"
                                + "and provides a structured, practical approach for addressing maladaptive thinking."),
                new Author(
                        "Irvin",
                        "Yalom",
                        dateFormat.parse("1931-06-13"),
                        dateFormat.parse("2024-05-22"),
                        "Irvin Yalom is an American existential psychiatrist and author known for his work in group therapy\n"
                                + "and existential psychotherapy. His writings explore the core existential concerns of life: death,\n"
                                + "freedom, isolation, and meaninglessness. Yalom’s books, both academic and fictional, offer insightful\n"
                                + "perspectives on the human condition and have profoundly influenced the practice of psychotherapy."),
                new Author(
                        "Daniel",
                        "Kahneman",
                        dateFormat.parse("1934-03-05"),
                        dateFormat.parse("2024-03-27"),
                        "Daniel Kahneman was an Israeli-American psychologist and economist notable for his work on the\n"
                                + "psychology of judgment and decision-making, as well as behavioral economics, for which he was awarded\n"
                                + "the 2002 Nobel Memorial Prize in Economic Sciences. His theories, including prospect theory and cognitive\n"
                                + "biases, have had a massive impact on how people understand and model human behavior.\n"
                                + "He is regarded as one of the most influential psychologists of the 21st century."),
                new Author(
                        "Martin",
                        "Seligman",
                        dateFormat.parse("1942-08-12"),
                        null,
                        "Martin Seligman is an American psychologist known for his work in positive psychology,\n"
                                + "a field that focuses on the study of human strengths and virtues. He initially studied learned\n"
                                + "helplessness but later developed theories of happiness, well-being, and resilience. Seligman's work\n"
                                + "has transformed the field of psychology by shifting the focus from pathology to the positive aspects\n"
                                + "of human experience."),
                new Author(
                        "Judith",
                        "Beck",
                        dateFormat.parse("1954-05-01"),
                        null,
                        "Judith Beck is an American cognitive therapist and author, known for her work in applying cognitive\n"
                                + "behavioral therapy (CBT) to various mental health disorders and the establishment of the Beck\n"
                                + "Institute for Cognitive Therapy and Research. She has written several books, including 'Cognitive\n"
                                + "Behavior Therapy: Basics and Beyond,' providing practical guidance and techniques for CBT, making her\n"
                                + "a key figure in the field of cognitive therapy."),
                new Author(
                        "Susan",
                        "David",
                        dateFormat.parse("1968-01-01"),
                        null,
                        "Susan David is a psychologist and author known for her work on emotional agility. She emphasizes\n"
                                + "the importance of embracing a full range of emotions and using them as sources of guidance.\n"
                                + "David's research and books have helped people develop resilience, manage difficult feelings,\n"
                                + "and lead more meaningful lives. Her frameworks for dealing with complex and challenging emotions\n"
                                + "have become influential in both the therapeutic and corporate worlds.")
        );
      } catch (ParseException e) {
        throw new RuntimeException(e);
      }

      authorRepository.saveAll(authors);
      System.out.println(authors.size() + " authors persisted!");

      if (authors != null) {
        Author freud = authors.get(0);
        Author jung = authors.get(1);
        Author frankl = authors.get(2);
        Author maslow = authors.get(3);
        Author ellis = authors.get(4);
        Author beck = authors.get(5);
        Author yalom = authors.get(6);
        Author kahneman = authors.get(7);
        Author seligman = authors.get(8);
        Author judithBeck = authors.get(9);
        Author susanDavid = authors.get(10);
        try {
          List<Book> books = Arrays.asList(
                  // Freud's books
                  new Book("The Interpretation of Dreams", "978-0486433143", dateFormat.parse("1899-11-04"), 600, "A groundbreaking work that introduces Freud's theory of the unconscious.", freud),
                  new Book("Civilization and Its Discontents", "978-0393304587", dateFormat.parse("1930-01-01"), 150, "Freud's exploration of the tensions between individual desires and societal expectations.", freud),
                  new Book("Beyond the Pleasure Principle", "978-0871409139", dateFormat.parse("1920-01-01"), 100, "Freud's exploration of the death drive and its implications for human behavior.", freud),
                  // Jung's books
                  new Book("Psychological Types", "978-0691150465", dateFormat.parse("1921-06-16"), 700, "Jung's seminal work on psychological types and individuation.", jung),
                  new Book("The Archetypes and the Collective Unconscious", "978-0691150489", dateFormat.parse("1968-01-01"), 500, "Jung's exploration of the archetypes and their role in the collective unconscious.", jung),
                  new Book("Memories, Dreams, Reflections", "978-0679723957", dateFormat.parse("1961-01-01"), 400, "Jung's autobiographical account of his life and work.", jung),
                  // Frankl's books
                  new Book("Man's Search for Meaning", "978-0807014295", dateFormat.parse("1946-01-01"), 200, "Frankl's powerful account of his experiences in concentration camps and his development of logotherapy.", frankl),
                  new Book("The Will to Meaning", "978-0452010222", dateFormat.parse("1969-01-01"), 180, "Frankl's exploration of the human search for meaning and purpose.", frankl),
                  new Book("The Unheard Cry for Meaning", "978-0671230423", dateFormat.parse("1978-01-01"), 170, "Frankl's views on the importance of meaning in therapeutic practice.", frankl),
                  // Maslow's books
                  new Book("Toward a Psychology of Being", "978-1503200107", dateFormat.parse("1968-01-01"), 250, "Maslow's exploration of self-actualization and human potential.", maslow),
                  new Book("Motivation and Personality", "978-0060419877", dateFormat.parse("1954-01-01"), 450, "Maslow's detailed explanation of his hierarchy of needs and the factors that motivate human behavior.", maslow),
                  new Book("The Farther Reaches of Human Nature", "978-0140170247", dateFormat.parse("1971-01-01"), 300, "Maslow's thoughts on human values, creativity, and higher consciousness.", maslow),
                  // Ellis's books
                  new Book("Reason and Emotion in Psychotherapy", "978-0818403806", dateFormat.parse("1962-01-01"), 350, "Ellis's foundational text on rational emotive behavior therapy (REBT).", ellis),
                  new Book("A Guide to Rational Living", "978-0879800427", dateFormat.parse("1961-01-01"), 280, "Ellis's guide for everyday living using REBT principles.", ellis),
                  new Book("Overcoming Destructive Beliefs, Feelings, and Behaviors", "978-0879804593", dateFormat.parse("1987-01-01"), 320, "Ellis's book offering solutions to help people overcome dysfunctional patterns.", ellis),
                  // Beck's books
                  new Book("Cognitive Therapy and the Emotional Disorders", "978-0823609900", dateFormat.parse("1976-01-01"), 400, "Beck's introduction to cognitive therapy and its applications to emotional disorders.", beck),
                  new Book("Cognitive Therapy of Depression", "978-0898620212", dateFormat.parse("1979-01-01"), 350, "Beck's groundbreaking work on cognitive therapy for depression.", beck),
                  new Book("Love Is Never Enough", "978-0060916872", dateFormat.parse("1988-01-01"), 200, "Beck's analysis of how cognitive therapy can be used to improve relationships.", beck),
                  // Yalom's books
                  new Book("The Theory and Practice of Group Psychotherapy", "978-0465092848", dateFormat.parse("1970-01-01"), 500, "Yalom's comprehensive guide to group therapy practice and theory.", yalom),
                  new Book("Existential Psychotherapy", "978-0465021770", dateFormat.parse("1980-01-01"), 450, "Yalom's exploration of existential themes in psychotherapy.", yalom),
                  new Book("When Nietzsche Wept", "978-0060931752", dateFormat.parse("1992-01-01"), 380, "Yalom's historical novel exploring the relationship between Nietzsche and Breuer.", yalom),
                  // Kahneman's books
                  new Book("Thinking, Fast and Slow", "978-0374275631", dateFormat.parse("2011-10-25"), 450, "Kahneman's overview of his work on cognitive biases and decision-making.", kahneman),
                  new Book("Attention and Effort", "978-0130505301", dateFormat.parse("1973-01-01"), 300, "Kahneman's psychological perspective on attention.", kahneman),
                  new Book("Judgment under Uncertainty: Heuristics and Biases", "978-0521284141", dateFormat.parse("1982-01-01"), 500, "A collection of Kahneman's articles on judgment and biases.", kahneman),
                  // Seligman's books
                  new Book("Learned Optimism", "978-0671019110", dateFormat.parse("1990-01-01"), 300, "Seligman's introduction to the principles of positive psychology and learned optimism.", seligman),
                  new Book("Authentic Happiness", "978-0743222985", dateFormat.parse("2002-01-01"), 350, "Seligman's guide to finding lasting happiness and fulfillment.", seligman),
                  new Book("Flourish", "978-1439190754", dateFormat.parse("2011-01-01"), 280, "Seligman's follow-up book on the concepts of positive psychology.", seligman),
                  // Judith Beck's books
                  new Book("Cognitive Behavior Therapy: Basics and Beyond", "978-1609185047", dateFormat.parse("2011-01-01"), 380, "Judith Beck's practical guide to cognitive behavior therapy (CBT) techniques and application.", judithBeck),
                  new Book("The Beck Diet Solution", "978-0843612769", dateFormat.parse("2007-01-01"), 320, "Judith Beck's book on using CBT for weight management.", judithBeck),
                  new Book("Cognitive Therapy for Challenging Problems", "978-1572309383", dateFormat.parse("2005-01-01"), 420, "Judith Beck's clinical guide to the application of CBT to complex problems.", judithBeck),
                  // Susan David's books
                  new Book("Emotional Agility", "978-1591848291", dateFormat.parse("2016-09-06"), 280, "Susan David's guide to navigating emotions with resilience and agility.", susanDavid),
                  new Book("That's How We Work", "978-0593542168", dateFormat.parse("2023-01-01"), 250, "Susan David's new book with a new perspective of emotions.", susanDavid),
                  new Book("Coaching for Emotional Agility", "978-0593195325", dateFormat.parse("2020-01-01"), 230, "Susan David's guide to coaching others through complex feelings.", susanDavid)


          );

          bookRepository.saveAll(books);

          System.out.println(books.size() + " books persisted!");
        }
        catch (ParseException e) {
          throw new RuntimeException(e);
        }
      }
    };
  }
}