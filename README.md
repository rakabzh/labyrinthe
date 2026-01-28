# 🧩 Générateur de labyrinthe en Java
Ce projet est un générateur et solveur de labyrinthes parfaits (un seul chemin possible entre deux points), écrit en Java.

🔹 Génération

La génération du labyrinthe repose sur l’algorithme Depth-First Search (DFS) avec backtracking, garantissant un labyrinthe parfait.
Le résultat est exporté sous forme d’une image PNG.

🔹 Résolution

La résolution du labyrinthe utilise un parcours en largeur (Breadth-First Search, BFS), permettant de trouver le plus court chemin entre l’entrée et la sortie.
Le chemin solution est ensuite reconstruit à partir des relations parent-enfant et affiché directement sur l’image.
