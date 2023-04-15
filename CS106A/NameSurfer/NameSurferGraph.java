
/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;

import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas implements NameSurferConstants, ComponentListener {
	private ArrayList<NameSurferEntry> entryArrayList = new ArrayList<NameSurferEntry>();

	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
	}

	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		entryArrayList.clear();
		removeAll();
		decadeLines();
		yearGLabels();
	}

	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display. Note that
	 * this method does not actually draw the graph, but simply stores the entry;
	 * the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		entryArrayList.add(entry);
		update();
	}

	/**
	 * Updates the display image by deleting all the graphical objects from the
	 * canvas and then reassembling the display according to the list of entries.
	 * Your application must call update after calling either clear or addEntry;
	 * update is also called whenever the size of the canvas changes.
	 */
	public void update() {
		removeAll();
		decadeLines();
		yearGLabels();
		graphing();
	}

	private void decadeLines() {
		for (int i = 0; i < NDECADES; i++) {
			int x = i * (getWidth() / NDECADES);
			GLine line = new GLine(x, 0, x, getHeight());
			add(line);
		}
		GLine bottomLine = new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE);
		add(bottomLine);
		GLine upperLine = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
		add(upperLine);
	}

	private void yearGLabels() {
		for (int i = 0; i < NDECADES; i++) {
			int x = 5 + i * (getWidth() / NDECADES);
			String s = String.valueOf(START_DECADE + i * 10);
			GLabel label = new GLabel(s, x, getHeight() - 5);
			add(label);
		}
	}

	private void graphing() {
		for (int j = 0; j < entryArrayList.size(); j++) {
			Color x = null;
			if (j % 4 == 0) {
				x = Color.BLACK;
			} else if (j % 4 == 1) {
				x = Color.RED;
			} else if (j % 4 == 2) {
				x = Color.BLUE;
			} else if (j % 4 == 3) {
				x = Color.YELLOW;
			}
			for (int i = 1; i < NDECADES; i++) {
				if (entryArrayList.get(j).getRank(i - 1) == 0 && entryArrayList.get(j).getRank(i) != 0) {
					GLine graphLine = new GLine((i - 1) * (getWidth() / NDECADES), getHeight() - GRAPH_MARGIN_SIZE,
							i * (getWidth() / NDECADES),
							(getHeight() - 2 * GRAPH_MARGIN_SIZE) * (entryArrayList.get(j)).getRank(i) / MAX_RANK
									+ GRAPH_MARGIN_SIZE);
					graphLine.setColor(x);
					add(graphLine);
				} else if (entryArrayList.get(j).getRank(i - 1) == 0 && entryArrayList.get(j).getRank(i - 1) == 0) {
					GLine graphLine = new GLine((i - 1) * (getWidth() / NDECADES), getHeight() - GRAPH_MARGIN_SIZE,
							i * (getWidth() / NDECADES), getHeight() - GRAPH_MARGIN_SIZE);
					graphLine.setColor(x);
					add(graphLine);
				} else if (entryArrayList.get(j).getRank(i - 1) != 0 && entryArrayList.get(j).getRank(i) == 0) {

					GLine graphLine = new GLine((i - 1) * (getWidth() / NDECADES),
							(getHeight() - 2 * GRAPH_MARGIN_SIZE) * (entryArrayList.get(j)).getRank(i - 1) / MAX_RANK
									+ GRAPH_MARGIN_SIZE,
							i * (getWidth() / NDECADES), getHeight() - GRAPH_MARGIN_SIZE);
					graphLine.setColor(x);
					add(graphLine);
				} else if (entryArrayList.get(j).getRank(i - 1) != 0 && entryArrayList.get(j).getRank(i - 1) != 0) {
					GLine graphLine = new GLine((i - 1) * (getWidth() / NDECADES),
							(getHeight() - 2 * GRAPH_MARGIN_SIZE) * (entryArrayList.get(j)).getRank(i - 1) / MAX_RANK
									+ GRAPH_MARGIN_SIZE,
							i * (getWidth() / NDECADES),
							(getHeight() - 2 * GRAPH_MARGIN_SIZE) * (entryArrayList.get(j)).getRank(i) / MAX_RANK
									+ GRAPH_MARGIN_SIZE);
					graphLine.setColor(x);
					add(graphLine);
				}
				if ((entryArrayList.get(j)).getRank(i - 1) == 0) {
					GLabel nameLabel = new GLabel(entryArrayList.get(j).getName() + "*",
							(i - 1) * (getWidth() / NDECADES) + 5, getHeight() - GRAPH_MARGIN_SIZE - 5);
					nameLabel.setColor(x);
					add(nameLabel);
				} else {
					GLabel nameLabel = new GLabel(
							entryArrayList.get(j).getName() + "(" + (entryArrayList.get(j)).getRank(i - 1) + ")",
							(i - 1) * (getWidth() / NDECADES) + 5,
							(getHeight() - 2 * GRAPH_MARGIN_SIZE) * (entryArrayList.get(j)).getRank(i - 1) / MAX_RANK
									+ GRAPH_MARGIN_SIZE);
					nameLabel.setColor(x);
					add(nameLabel);
				}

				// for last decade GLabels
				if (i == NDECADES - 1) {
					if ((entryArrayList.get(j)).getRank(i) == 0) {
						GLabel nameLabel = new GLabel(entryArrayList.get(j).getName() + "*",
								(i) * (getWidth() / NDECADES) + 5, getHeight() - GRAPH_MARGIN_SIZE - 5);
						nameLabel.setColor(x);
						add(nameLabel);
					} else {
						GLabel nameLabel = new GLabel(
								entryArrayList.get(j).getName() + "(" + (entryArrayList.get(j)).getRank(i) + ")",
								(i) * (getWidth() / NDECADES) + 5,
								(getHeight() - 2 * GRAPH_MARGIN_SIZE) * (entryArrayList.get(j)).getRank(i) / MAX_RANK
										+ GRAPH_MARGIN_SIZE);
						nameLabel.setColor(x);
						add(nameLabel);
					}
				}
			}
		}
	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		update();
	}

	public void componentShown(ComponentEvent e) {
	}
}
