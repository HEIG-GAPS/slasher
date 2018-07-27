package ch.gaps.slasher;


import ch.gaps.slasher.highliter.Highlighter;

import java.util.LinkedList;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Loads the {@link Highlighter}s by using the {@link ServiceLoader} API.
 *
 * @author Jérôme Varani, Alexandra Korukova
 */
public final class HighlighterService {

    /**
     * The {@link HighlighterService}.
     */
    private static HighlighterService service;

    /**
     * The {@link ServiceLoader} that loads {@link Highlighter}s.
     */
    private final ServiceLoader<Highlighter> loader;

    /**
     * Constructs a new {@link HighlighterService} instance. This constructor is
     * private to be only call by the singleton pattern.
     */
    private HighlighterService( ) { loader = ServiceLoader.load(Highlighter.class); }

    /**
     * @return the unique instance of {@link HighlighterService}.
     */
    public static synchronized HighlighterService instance() {
        if (service == null)
            service = new HighlighterService();
        return service;
    }

    /**
     * @return all {@link Highlighter}s that have been loaded by this service.
     */
    public LinkedList<Highlighter> getAll() {
        LinkedList<Highlighter> l = new LinkedList<>();
        try {
            for (Highlighter h : loader) l.add(h);
        } catch (ServiceConfigurationError serviceError) {
            Logger.getLogger(DriverService.class.getName()).log(Level.SEVERE, null,
                    serviceError);
        }
        return l;
    }
}
