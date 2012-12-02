package com.robodex.app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

public class Hyperlink {

	/** Will only make text appear as a link (blue, underlined) */
	public static SpannableString create(CharSequence displayText) {
		return create(displayText, null);
	}

	/**
	 * Create a hyperlink string. Will not be clickable until makeLinksClickable(TextView) is
	 * called on a containing TextView.
	 */
	public static SpannableString create(CharSequence displayText, final String hiddenLink) {
		SpannableString link = new SpannableString(displayText);
		if (hiddenLink != null && hiddenLink.length() > 0) {
			Linkify.TransformFilter filter = new Linkify.TransformFilter() {
	            @Override public String transformUrl(Matcher match, String url) {
	                return hiddenLink;
	            }
	        };
	        Linkify.addLinks(link, Pattern.compile(".+"), null, null, filter);
		}
		else Linkify.addLinks(link, Linkify.ALL);
		return link;
	}

	/**
	 * Set a clickable url into the given TextView.
	 */
	public static void setText(TextView container, CharSequence displayText) {
		setText(container, displayText, null);
	}

	/**
	 * Set a clickable url into the given TextView.
	 */
	public static void setText(TextView container, CharSequence displayText, final String hiddenLink) {
		SpannableString link = create(displayText, hiddenLink);
		if (container != null) {
			container.setText(link);
			makeLinksClickable(container);
		}
	}


	/**
	 * Append a clickable url into the given TextView.
	 */
	public static void append(TextView container, CharSequence displayText) {
		append(container, displayText, null);
	}

	/**
	 * Append a clickable url into the given TextView.
	 */
	public static void append(TextView container, CharSequence displayText, final String hiddenLink) {
		SpannableString link = create(displayText, hiddenLink);
		if (container != null) {
			container.append(link);
			makeLinksClickable(container);
		}
	}

	public static void makeLinksClickable(TextView tv) {
		tv.setMovementMethod(LinkMovementMethod.getInstance());
	}
}
