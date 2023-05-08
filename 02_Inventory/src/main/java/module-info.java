module inventory {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;

    opens inventory.model to javafx.base;
    exports inventory.model;
    opens inventory to javafx.fxml;
    exports inventory;
    exports inventory.service;
    exports inventory.repository;
    opens inventory.controller to javafx.fxml;
    exports inventory.controller;
}