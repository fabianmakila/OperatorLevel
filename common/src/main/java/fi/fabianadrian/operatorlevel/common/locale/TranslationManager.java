package fi.fabianadrian.operatorlevel.common.locale;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationStore;
import org.slf4j.Logger;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public final class TranslationManager {
	public static final List<Locale> BUNDLED_LOCALES = List.of(Locale.ENGLISH, Locale.of("fi", "FI"));

	private final Logger logger;
	private final TranslationStore.StringBased<MessageFormat> store;

	public TranslationManager(Logger logger) {
		this.logger = logger;

		this.store = TranslationStore.messageFormat(Key.key("operatorlevel", "main"));
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
