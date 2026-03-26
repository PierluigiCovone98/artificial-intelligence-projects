# Artificial Intelligence

Projects for the Artificial Intelligence course (A.Y. 2025/2026).

---

## Testing

### 1. Run the script
```bash
./scripts/run.sh
```

### 2. Follow the interactive menu

The script:

1. **Compiles** the project automatically (if not already compiled). The `.class` files are generated in the `out/` directory.
2. Presents the available **problem categories**.
3. Within each category, shows the **executable tests**.
4. After each test, allows running another one, going back to the main menu, or exiting.

### Recompile after source changes

If you modify any `.java` file, run the reset script
```bash
./scripts/reset.sh
```
and then run again
```bash
./scripts/run.sh
```


---

## Implemented Problems

### State Space Exploration Problems

| Problem | Description |
|---------|-------------|
| **Romania Trip** | Find the optimal path from a city (Arad) to Bucharest on the Romania road map. |
| **HP 2D Protein Folding** | Find the optimal conformation of a protein (H/P sequence) on a 2D grid, maximizing H-H contacts. |