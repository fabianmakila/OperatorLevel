package fi.fabianadrian.operatorlevel.common.locale;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.minimessage.translation.MiniMessageTranslationStore;
import net.kyori.adventure.translation.GlobalTranslator;
import org.slf4j.Logger;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public final class TranslationManager {
	public static final List<Locale> BUNDLED_LOCALES = List.of(Locale.ENGLISH, Locale.of("fi", "FI"));

	private final Logger logger;
	private final MiniMessageTranslationStore store;

	public TranslationManager(Logger logger) {
		this.logger = logger;

		this.store = MiniMessageTranslationStore.create(Key.key("operatorlevel", "main"));
		this.store.defaultLocale(Locale.ENGLISH);

		loadFromResourceBundle();

		GlobalTranslator.translator().addSource(this.store);
	}

	private void loadFromResourceBundle() {
		BUNDLED_LOCALES.forEach(locale -> {
			ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
			try {
				this.store.registerAll(locale, bundle, false);
			} catch (IllegalArgumentException e) {
				this.logger.warn("Error loading default locale file", e);
			}
		});

	}
}
