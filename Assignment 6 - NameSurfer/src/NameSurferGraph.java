/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import acm.util.RandomGenerator;

import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {

	public NameSurferGraph() {
		addComponentListener(this);
		update();
	}
	
	public void clear() {
		nameList.clear();
		update();
	}
	
	public void addEntry(NameSurferEntry entry) {
		nameList.add(entry);
		update();
	}

	public void update() {
		removeAll();
		drawVertical(); //Create and add all vertical lines
		drawHorizontal(); //Create and add the tow horizontal lines
		drawYearLabel(); //Create and adding year labels
		drawGraph(); //Drawing the graph if there is any names in the namesList array
	}
	
	private void drawVertical() {
		for (int i = 1; i < NDECADES ; i++) {
			int currentXPos = getWidth() / NDECADES * i;
			verticalLine = new GLine(currentXPos, 0, currentXPos, getHeight());
			add(verticalLine);
		}
	}
	
	private void drawYearLabel() {
		for (int i = 0; i < NDECADES ; i ++) {
			int currentXPos = getWidth() / NDECADES * i;
			yearLabel = new GLabel("" + (START_DECADE + (YEAR_INCREASE * i)), currentXPos, getHeight());
			add(yearLabel);
		}
	}
	
	private void drawHorizontal() {
		horizontalLine1 = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
		horizontalLine2 = new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE);
		add(horizontalLine1); add(horizontalLine2);
		
	}
	
	private void drawGraph() {
				
				if (!nameList.isEmpty()) {
					for (int i = 0; i < nameList.size() ; i++) {
						int currentYear = START_DECADE;
						int nextYear = START_DECADE + YEAR_INCREASE;
						String name = nameList.get(i).getName();
						
						if (i == 0) {
							color = Color.RED;
							}
						if (i == 1) {
							color = Color.BLUE;
							}
						if (i == 2) {
							color = Color.GREEN;
							}

						
						for(int j = 0; j < NDECADES - 1 ; j++) {
							//Calculating x position
							double x1 = getWidth() / NDECADES * j;
							double x2 = x1 + getWidth() / NDECADES;
							
							//Setting the new ranks for the decade
							int currentRank = (nameList.get(i).getRank(currentYear));
							int nextRank = (nameList.get(i).getRank(nextYear));
							
							//Calculating y position
							double yEachPoint = ((double)getHeight() - (2 * GRAPH_MARGIN_SIZE)) / MAX_RANK;
							double y1 = GRAPH_MARGIN_SIZE + currentRank * yEachPoint;
							double y2 = GRAPH_MARGIN_SIZE + nextRank * yEachPoint;
							
							//Creating the string of the label
							String rankLabel = name + " " + currentRank;
							
							//Checking if the rank was 0, then its positioned at the bottom.
							if (y1 == GRAPH_MARGIN_SIZE) { 
								y1 = getHeight() - GRAPH_MARGIN_SIZE; 
								rankLabel = "*";
								}
							if (y2 == GRAPH_MARGIN_SIZE) { 
								y2 = getHeight() - GRAPH_MARGIN_SIZE;
								}
							
							//Creating and adding the label
							GLabel label = new GLabel(rankLabel);
							add(label, x1, y1);
							
							//Checking if its the last year in the graph, then adding the last label.
							if (nextYear == END_DECADE - YEAR_INCREASE) {
								String lastRankLabel = name + " " + nextRank;
								GLabel label2 = new GLabel(lastRankLabel);
								add(label2, x2, y2);
								}

							//Adding the line
							GLine line = new GLine(x1, y1, x2, y2);
							line.setColor(color);
							add(line);
							
							//Changing the years to get the new ranking and position
							currentYear += YEAR_INCREASE;
							nextYear += YEAR_INCREASE;
						}

					}
				}	
	}
	
	//Instance Variables
	private ArrayList<NameSurferEntry> nameList = new ArrayList<NameSurferEntry>();
	
	private GLine verticalLine;
	private GLine horizontalLine1;
	private GLine horizontalLine2;
	private GLabel yearLabel;
	private Color color;
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }

}
