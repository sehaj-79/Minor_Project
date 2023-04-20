package com.aliferous.minorproject;

import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private ImageView floorPlanImageView;
    private CustomView customView;
    private List<PointF> nodes = Arrays.asList(
            new PointF(100, 985+75), // Node 0
            new PointF(187, 985+75), // Node 1
            new PointF(275, 985+75), // Node 2     Junction
            new PointF(280, 900+75), // Node 3     Junction
            new PointF(280, 770+75), // Node 4
            new PointF(280, 640+75), // Node 5
            new PointF(280, 500+75), // Node 6
            new PointF(410, 500+75), // Node 7
            new PointF(550, 500+75), // Node 8
            new PointF(685, 500+75), // Node 9
            new PointF(830, 500+75), // Node 10
            new PointF(835, 640+75), // Node 11
            new PointF(835, 770+75), // Node 12
            new PointF(835, 895+75), // Node 13     Junction
            new PointF(830, 1060+75), // Node 14    Junction
            new PointF(825, 1180+75), // Node 15
            new PointF(825, 1290+75), // Node 16
            new PointF(825, 1385+75), // Node 17
            new PointF(685, 1385+75), // Node 18
            new PointF(550, 1385+75), // Node 19
            new PointF(410, 1385+75), // Node 20
            new PointF(280, 1385+75), // Node 21
            new PointF(275, 1250+75), // Node 22
            new PointF(275, 1120+75), // Node 23
            new PointF(930, 895+75), // Node 24
            new PointF(1020, 895+75), // Node 25
            new PointF(730, 1025+75), // Node 26
            new PointF(650, 930+75), // Node 27
            new PointF(520, 900+75), // Node 28
            new PointF(460, 970+75), // Node 29
            new PointF(370, 940+75) // Node 30
            // Add more nodes here as needed
    );

    private List<Pair<PointF, PointF>> edges = Arrays.asList(
            // Define edges between nodes as pairs of PointF objects
            new Pair<>(nodes.get(0), nodes.get(1)), 
            new Pair<>(nodes.get(1), nodes.get(2)),
            new Pair<>(nodes.get(2), nodes.get(3)),
            new Pair<>(nodes.get(3), nodes.get(4)),
            new Pair<>(nodes.get(4), nodes.get(5)),
            new Pair<>(nodes.get(5), nodes.get(6)),
            new Pair<>(nodes.get(6), nodes.get(7)),
            new Pair<>(nodes.get(7), nodes.get(8)),
            new Pair<>(nodes.get(8), nodes.get(9)),
            new Pair<>(nodes.get(9), nodes.get(10)),
            new Pair<>(nodes.get(10), nodes.get(11)),
            new Pair<>(nodes.get(11), nodes.get(12)),
            new Pair<>(nodes.get(12), nodes.get(13)),
            new Pair<>(nodes.get(13), nodes.get(14)),
            new Pair<>(nodes.get(14), nodes.get(15)),
            new Pair<>(nodes.get(15), nodes.get(16)),
            new Pair<>(nodes.get(16), nodes.get(17)),
            new Pair<>(nodes.get(17), nodes.get(18)),
            new Pair<>(nodes.get(18), nodes.get(19)),
            new Pair<>(nodes.get(19), nodes.get(20)),
            new Pair<>(nodes.get(20), nodes.get(21)),
            new Pair<>(nodes.get(21), nodes.get(22)),
            new Pair<>(nodes.get(22), nodes.get(23)),
            new Pair<>(nodes.get(23), nodes.get(2)),
            new Pair<>(nodes.get(13), nodes.get(24)),
            new Pair<>(nodes.get(24), nodes.get(25)),
            new Pair<>(nodes.get(14), nodes.get(26)),
            new Pair<>(nodes.get(26), nodes.get(27)),
            new Pair<>(nodes.get(27), nodes.get(28)),
            new Pair<>(nodes.get(28), nodes.get(29)),
            new Pair<>(nodes.get(29), nodes.get(30)),
            new Pair<>(nodes.get(30), nodes.get(3))

    );


    private List<PointF> getNeighbors(PointF node) {
        List<PointF> neighbors = new ArrayList<>();
        for (Pair<PointF, PointF> edge : edges) {
            if (edge.first.equals(node)) {
                neighbors.add(edge.second);
            } else if (edge.second.equals(node)) {
                neighbors.add(edge.first);
            }
        }
        return neighbors;
    }


    private PointF sourceNode;
    private PointF destinationNode;
    private List<PointF> path;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floorPlanImageView = findViewById(R.id.floorPlanImageView);
        customView = findViewById(R.id.customView);

        customView.setNodes(nodes);

        floorPlanImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    PointF tappedPoint = new PointF(motionEvent.getX(), motionEvent.getY());
                    PointF nearestNode = nearestNode(tappedPoint);

                    if (nearestNode != null) {
                        if (sourceNode == null) {
                            sourceNode = nearestNode;
                        } else if (destinationNode == null) {
                            destinationNode = nearestNode;
                            path = findPath(sourceNode, destinationNode, nodes);
                            customView.setPath(path);
                        } else {
                            sourceNode = nearestNode;
                            destinationNode = null;
                            path = new ArrayList<>();
                            customView.setPath(path);
                        }
                    }
                }
                return true;
            }
        });
    }

    private PointF nearestNode(PointF location) {
        PointF closestNode = null;
        float minDistance = Float.MAX_VALUE;

        for (PointF node : nodes) {
            float distance = distanceBetween(location, node);
            if (distance < minDistance) {
                minDistance = distance;
                closestNode = node;
            }
        }

        return closestNode;
    }

    private float distanceBetween(PointF point1, PointF point2) {
        return (float) Math.hypot(point1.x - point2.x, point1.y - point2.y);
    }

    private List<PointF> findPath(PointF start, PointF end, List<PointF> nodes) {
        List<PointF> openList = new ArrayList<>();
        Set<PointF> closedList = new HashSet<>();

        Map<PointF, PointF> cameFrom = new HashMap<>();
        Map<PointF, Float> gScore = new HashMap<>();
        Map<PointF, Float> fScore = new HashMap<>();

        openList.add(start);
        gScore.put(start, 0f);
        fScore.put(start, heuristicCostEstimate(start, end));

        while (!openList.isEmpty()) {
            PointF current = getLowestFScore(openList, fScore);
            if (current.equals(end)) {
                return reconstructPath(cameFrom, current);
            }

            openList.remove(current);
            closedList.add(current);

            List<PointF> neighbors = getNeighbors(current);
            for (PointF neighbor : neighbors) {
                if (closedList.contains(neighbor)) {
                    continue;
                }

                float tentativeGScore = gScore.getOrDefault(current, Float.MAX_VALUE) + distanceBetween(current, neighbor);
                if (!openList.contains(neighbor)) {
                    openList.add(neighbor);
                } else if (tentativeGScore >= gScore.getOrDefault(neighbor, Float.MAX_VALUE)) {
                    continue;
                }

                cameFrom.put(neighbor, current);
                gScore.put(neighbor, tentativeGScore);
                fScore.put(neighbor, gScore.get(neighbor) + heuristicCostEstimate(neighbor, end));
            }
        }

        return new ArrayList<>();
    }

    private PointF getLowestFScore(List<PointF> openList, Map<PointF, Float> fScore) {
        PointF lowestFScoreNode = null;
        float lowestFScore = Float.MAX_VALUE;

        for (PointF node : openList) {
            float currentFScore = fScore.getOrDefault(node, Float.MAX_VALUE);
            if (currentFScore < lowestFScore) {
                lowestFScore = currentFScore;
                lowestFScoreNode = node;
            }
        }

        return lowestFScoreNode;
    }

    private List<PointF> reconstructPath(Map<PointF, PointF> cameFrom, PointF current) {
        List<PointF> path = new ArrayList<>();
        path.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(current);
        }
        Collections.reverse(path);
        return path;
    }

    private float heuristicCostEstimate(PointF start, PointF end) {
        return distanceBetween(start, end);
    }

}

