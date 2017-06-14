
package com.sseltzer.selenium.framework.jsoup.elements;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;

/**
 *
 *
 * Element.java
 *
 * @author ckiehl Aug 20, 2014
 */
public class WebElement {
	
	private final org.jsoup.nodes.Element _e;

	public WebElement(Element _e) {
		this._e = _e;
	}

	public String absUrl(String arg0) {
		return _e.absUrl(arg0);
	}

	public Element addClass(String className) {
		return _e.addClass(className);
	}

	public Element after(Node node) {
		return _e.after(node);
	}

	public Element after(String html) {
		return _e.after(html);
	}

	public Element append(String html) {
		return _e.append(html);
	}

	public Element appendChild(Node child) {
		return _e.appendChild(child);
	}

	public Element appendElement(String tagName) {
		return _e.appendElement(tagName);
	}

	public Element appendText(String text) {
		return _e.appendText(text);
	}

	public Element attr(String attributeKey, String attributeValue) {
		return _e.attr(attributeKey, attributeValue);
	}

	public String attr(String attributeKey) {
		return _e.attr(attributeKey);
	}

	public Attributes attributes() {
		return _e.attributes();
	}

	public String baseUri() {
		return _e.baseUri();
	}

	public Element before(Node node) {
		return _e.before(node);
	}

	public Element before(String html) {
		return _e.before(html);
	}

	public Element child(int index) {
		return _e.child(index);
	}

	public Node childNode(int index) {
		return _e.childNode(index);
	}

	public final int childNodeSize() {
		return _e.childNodeSize();
	}

	public List<Node> childNodes() {
		return _e.childNodes();
	}

	public List<Node> childNodesCopy() {
		return _e.childNodesCopy();
	}

	public Elements children() {
		return _e.children();
	}

	public String className() {
		return _e.className();
	}

	public Set<String> classNames() {
		return _e.classNames();
	}

	public Element classNames(Set<String> classNames) {
		return _e.classNames(classNames);
	}

	public Element clone() {
		return _e.clone();
	}

	public String data() {
		return _e.data();
	}

	public List<DataNode> dataNodes() {
		return _e.dataNodes();
	}

	public Map<String, String> dataset() {
		return _e.dataset();
	}

	public Integer elementSiblingIndex() {
		return _e.elementSiblingIndex();
	}

	public Element empty() {
		return _e.empty();
	}

	public boolean equals(Object o) {
		return _e.equals(o);
	}

	public Element firstElementSibling() {
		return _e.firstElementSibling();
	}

	public Elements getAllElements() {
		return _e.getAllElements();
	}

	public Element getElementById(String id) {
		return _e.getElementById(id);
	}

	public Elements getElementsByAttribute(String key) {
		return _e.getElementsByAttribute(key);
	}

	public Elements getElementsByAttributeStarting(String keyPrefix) {
		return _e.getElementsByAttributeStarting(keyPrefix);
	}

	public Elements getElementsByAttributeValue(String key, String value) {
		return _e.getElementsByAttributeValue(key, value);
	}

	public Elements getElementsByAttributeValueContaining(String key, String match) {
		return _e.getElementsByAttributeValueContaining(key, match);
	}

	public Elements getElementsByAttributeValueEnding(String key, String valueSuffix) {
		return _e.getElementsByAttributeValueEnding(key, valueSuffix);
	}

	public Elements getElementsByAttributeValueMatching(String key, Pattern pattern) {
		return _e.getElementsByAttributeValueMatching(key, pattern);
	}

	public Elements getElementsByAttributeValueMatching(String arg0, String arg1) {
		return _e.getElementsByAttributeValueMatching(arg0, arg1);
	}

	public Elements getElementsByAttributeValueNot(String key, String value) {
		return _e.getElementsByAttributeValueNot(key, value);
	}

	public Elements getElementsByAttributeValueStarting(String key, String valuePrefix) {
		return _e.getElementsByAttributeValueStarting(key, valuePrefix);
	}

	public Elements getElementsByClass(String className) {
		return _e.getElementsByClass(className);
	}

	public Elements getElementsByIndexEquals(int index) {
		return _e.getElementsByIndexEquals(index);
	}

	public Elements getElementsByIndexGreaterThan(int index) {
		return _e.getElementsByIndexGreaterThan(index);
	}

	public Elements getElementsByIndexLessThan(int index) {
		return _e.getElementsByIndexLessThan(index);
	}

	public Elements getElementsByTag(String tagName) {
		return _e.getElementsByTag(tagName);
	}

	public Elements getElementsContainingOwnText(String searchText) {
		return _e.getElementsContainingOwnText(searchText);
	}

	public Elements getElementsContainingText(String searchText) {
		return _e.getElementsContainingText(searchText);
	}

	public Elements getElementsMatchingOwnText(Pattern pattern) {
		return _e.getElementsMatchingOwnText(pattern);
	}

	public Elements getElementsMatchingOwnText(String arg0) {
		return _e.getElementsMatchingOwnText(arg0);
	}

	public Elements getElementsMatchingText(Pattern pattern) {
		return _e.getElementsMatchingText(pattern);
	}

	public Elements getElementsMatchingText(String arg0) {
		return _e.getElementsMatchingText(arg0);
	}

	public boolean hasAttr(String arg0) {
		return _e.hasAttr(arg0);
	}

	public boolean hasClass(String arg0) {
		return _e.hasClass(arg0);
	}

	public boolean hasText() {
		return _e.hasText();
	}

	public int hashCode() {
		return _e.hashCode();
	}

	public String html() {
		return _e.html();
	}

	public Element html(String html) {
		return _e.html(html);
	}

	public String id() {
		return _e.id();
	}

	public Element insertChildren(int index, Collection<? extends Node> children) {
		return _e.insertChildren(index, children);
	}

	public boolean isBlock() {
		return _e.isBlock();
	}

	public Element lastElementSibling() {
		return _e.lastElementSibling();
	}

	public Element nextElementSibling() {
		return _e.nextElementSibling();
	}

	public Node nextSibling() {
		return _e.nextSibling();
	}

	public String nodeName() {
		return _e.nodeName();
	}

	public String outerHtml() {
		return _e.outerHtml();
	}

	public String ownText() {
		return _e.ownText();
	}

	public Document ownerDocument() {
		return _e.ownerDocument();
	}

	public final Element parent() {
		return _e.parent();
	}

	public final Node parentNode() {
		return _e.parentNode();
	}

	public Elements parents() {
		return _e.parents();
	}

	public Element prepend(String html) {
		return _e.prepend(html);
	}

	public Element prependChild(Node child) {
		return _e.prependChild(child);
	}

	public Element prependElement(String tagName) {
		return _e.prependElement(tagName);
	}

	public Element prependText(String text) {
		return _e.prependText(text);
	}

	public Element previousElementSibling() {
		return _e.previousElementSibling();
	}

	public Node previousSibling() {
		return _e.previousSibling();
	}

	public void remove() {
		_e.remove();
	}

	public Node removeAttr(String attributeKey) {
		return _e.removeAttr(attributeKey);
	}

	public Element removeClass(String className) {
		return _e.removeClass(className);
	}

	public void replaceWith(Node in) {
		_e.replaceWith(in);
	}

	public Elements select(String cssQuery) {
		return _e.select(cssQuery);
	}

	public void setBaseUri(String baseUri) {
		_e.setBaseUri(baseUri);
	}

	public Elements siblingElements() {
		return _e.siblingElements();
	}

	public int siblingIndex() {
		return _e.siblingIndex();
	}

	public List<Node> siblingNodes() {
		return _e.siblingNodes();
	}

	public Tag tag() {
		return _e.tag();
	}

	public String tagName() {
		return _e.tagName();
	}

	public Element tagName(String tagName) {
		return _e.tagName(tagName);
	}

	public String text() {
		return _e.text();
	}

	public Element text(String text) {
		return _e.text(text);
	}

	public List<TextNode> textNodes() {
		return _e.textNodes();
	}

	public String toString() {
		return _e.toString();
	}

	public Element toggleClass(String className) {
		return _e.toggleClass(className);
	}

	public Node traverse(NodeVisitor nodeVisitor) {
		return _e.traverse(nodeVisitor);
	}

	public Node unwrap() {
		return _e.unwrap();
	}

	public String val() {
		return _e.val();
	}

	public Element val(String value) {
		return _e.val(value);
	}

	public Element wrap(String html) {
		return _e.wrap(html);
	}
	
	
	
	
	
	
	
}
