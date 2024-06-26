# bootsimulator
Readme:
Diese Anwendung simuliert jeweils zwei Kurse vom Standort des Motorbootes zu einem beliebig gewählten Ziel.
Dazu werden zwei Routingstrategien verwendet.
Die Optimale Routenfindung, welche mittels Vektorrechnung den Kurs bestimmt, der gefahren werden muss um das Ziel zu erreichen.
Um die verschiedenen Strömungen im Wasser auszugleichen, muss gegebenenfalls die Richtung vorgehalten werden.
Die regelmäßige Routenanpassung allerdings ändert in regelmäßigen Abstanden ihren Kurs immer direkt auf das Ziel zu.
Dadurch wird das Boot im wieder von der Strömung abgetrieben, wodurch der Kurs neu gesetzt werden muss.

In der Main Klasse kann das Attribut „waterVelocity“ angepasst werden, um die Geschwindigkeit in Knoten für das
Motorboot anzupassen.
Minum waterVelocity: 3.0

Das Attribut cordA steht für den Standort bzw. dem Startpunkt des Bootes. Das Attribut cordB steht für das Ziel.
Hierbei ist zu beachten, dass aufgrund der Größe des Frames sowie der vorhanden Strömungsdaten keine zu große Differenz
zwischen Start- und Zielpunkt bezüglich der Koordinaten angegeben werden darf.
Maximale Lat-Differenz: 0.5
Maximale Lon-Differenz: 0.28

Die Klasse MapCalculation gibt die Grundlage für alle weiteren Berechnungen, indem sie die Richtung und Distanz
zum Ziel berechnet. Hierzu gibt es einige Möglichkeiten und Formeln, um die korrekten Werte zu erhalten. Nach Tests
und Vergleiche der Werte mit Seekarten habe ich mich bei der Distanz-Berechnungen für die Haversinen Formel und bei
der Richtungs-Berechnung für die Vincenty Formel entschieden. Diese Formeln wurden aus den Quellen kopiert und für
die individuellen Bedürfnisse angepasst.

In der StreamCalculation Klasse werden die Routenstrategien berechnet und die Vektoren, welche die zurückgelegte
Strecke in 15 Minuten takt repräsentieren in einer Liste gespeichert.
Die Strömungsdaten auf die zurückgegriffen wird, sind in Textdateien hinterlegt. Jede Textdatei deckte jedes Feld
des Koordinatensystems mit einer unterschiedlichen Strömung ab und stellt die Strömung für die nächsten 15 Minuten
dar. Hierbei sollte in Zukunft eine Schnittstelle verwendet werden, welche realistische Strömungswerte für den
angegeben Bereich verwendet. Für die aktuellen Zwecke reicht aber Strömungsdaten in dieser Form aus.

Die UserInterface Klasse stellt das JFrame sowie die Klasse CoordinateSystem als JPanel. Diese holt sich die Listen
aus der StreamCalculation und stellt die Vektoren im Koordinatensystem als bereits zurückgelegte Streckenabschnitte
dar. Das Koordinatensystem ist in 30x30 Felder aufgeteilt und jedes Feld zeigt seine Strömung mit Richtung und Stärke
anhand der Richtung und Länge der Linie an.

Die Vector Klasse stellt alle notwendigen Methoden zur Verfügung, welche für sämtliche Vektorrechnungen benötigt wurden.

Der StreamDataManager liest die Textdateien ein, welche die Strömungsdaten beinhalten und stellt Methoden zur
Verfügung, diese Daten in einer Liste zurückzugeben sowie einzelne Elemente aus den Listen auszulesen.


