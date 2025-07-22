package fi.fabianadrian.operatorlevel.common.locale;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.minimessage.translation.MiniMessageTranslationStore;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.Translator;
import net.kyori.adventure.util.UTF8ResourceBundleControl;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public final class TranslationManager {
	private static final List<Locale> BUNDLED_LOCALES = List.of(Locale.US, Locale.of("fi", "FI"));
	private final Path localeDirectoryPath;

	private final Logger logger;
	private MiniMessageTranslationStore store;

	public TranslationManager(Logger logger, Path localeDirectoryPath) {
		this.logger = logger;
		this.localeDirectoryPath = localeDirectoryPath;
	}

	private static boolean isAdventureDuplicatesException(Exception e) {
		return e instanceof IllegalArgumentException && (e.getMessage().startsWith("Invalid key") || e.getMessage().startsWith("Translation already exists"));
	}

	public void load() {
		if (this.store != null) {
			GlobalTranslator.translator().removeSource(this.store);
		}

		this.store = MiniMessageTranslationStore.create(Key.key("operatorlevel", "main"));

		createLocaleDirectory();
		copyToLocaleDirectory();
		registerFromLocaleDirectory();
		registerDefaultLocale();

		GlobalTranslator.translator().addSource(this.store);
	}

	private void createLocaleDirectory() {
		try {
			Files.createDirectories(this.localeDirectoryPath);
		} catch (IOException e) {
			this.logger.error("Couldn't create locale directory", e);
		}
	}

	private void copyToLocaleDirectory() {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.localeDirectoryPath, "*.properties")) {
			if (stream.iterator().hasNext()) {
				return;
			}
		} catch (IOException e) {
			this.logger.error("Couldn't read locale directory");
			return;
		}

		BUNDLED_LOCALES.forEach(locale -> {
			String fileName = "messages_" + locale.getLanguage() + ".properties";
			Path defaultBundlePath = this.localeDirectoryPath.resolve(fileName);
			if (Files.exists(defaultBundlePath)) {
				return;
			}

			try {
				Files.copy(this.getClass().getClassLoader().getResourceAsStream(fileName), defaultBundlePath);
			} catch (IOException e) {
				this.logger.error("Couldn't write bundled locale", e);
			}
		});
	}

	private void registerFromLocaleDirectory() {
		StringJoiner loadedLocaleNamesJoiner = new StringJoiner(", ");

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.localeDirectoryPath, "*.properties")) {
			for (Path localeFilePath : stream) {
				String fileName = localeFilePath.getFileName().toString();
				if (!fileName.startsWith("messages_")) {
					this.logger.warn("Couldn't load {}. Locale files must follow specified naming convention", fileName);
					continue;
				}

				Locale locale = parseLocaleFromFileName(fileName);
				loadedLocaleNamesJoiner.add(locale.getLanguage());

				ResourceBundle bundle;
				try (BufferedReader reader = Files.newBufferedReader(localeFilePath, StandardCharsets.UTF_8)) {
					bundle = new PropertyResourceBundle(reader);
				}

				this.store.registerAll(locale, bundle, false);
			}

			if (loadedLocaleNamesJoiner.length() != 0) {
				this.logger.info("Loaded locales: {}", loadedLocaleNamesJoiner);
			}
		} catch (IOException e) {
			this.logger.warn("Couldn't read the locale directory", e);
		}
	}

	private void registerDefaultLocale() {
		ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.US, UTF8ResourceBundleControl.get());
		try {
			this.store.registerAll(Locale.US, bundle, false);
		} catch (IllegalArgumentException e) {
			if (isAdventureDuplicatesException(e)) {
				return;
			}
			this.logger.warn("Error registering default locale", e);
		}
	}

	private Locale parseLocaleFromFileName(String fileName) {
		String localeString = fileName.substring(
				"messages_".length(),
				fileName.length() - ".properties".length()
		);
		Locale locale = Translator.parseLocale(localeString);
		if (locale == null) {
			throw new IllegalStateException("Couldn't parse locale for file name: " + fileName);
		}
		return locale;
	}
}
