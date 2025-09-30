import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TodoListApp {
    public static void main(String[] args) {
        // Create and show the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new TodoListGUI().setVisible(true);
        });
    }
}

class TodoListGUI extends JFrame {
    private DefaultListModel<String> listModel;
    private JList<String> taskList;
    private JTextField taskInput;
    private JButton addButton;
    private JButton deleteButton;
    private JButton clearAllButton;
    
    public TodoListGUI() {
        initializeGUI();
    }
    
    private void initializeGUI() {
        // Set up the main frame
        setTitle("To-Do List Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null); // Center the window
        
        // Create components
        createComponents();
        
        // Layout components
        layoutComponents();
        
        // Add event listeners
        addEventListeners();
    }
    
    private void createComponents() {
        // Create list model and JList
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Create input field
        taskInput = new JTextField();
        taskInput.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Create buttons
        addButton = new JButton("Add Task");
        deleteButton = new JButton("Delete Selected");
        clearAllButton = new JButton("Clear All");
        
        // Style buttons
        styleButton(addButton, Color.GREEN);
        styleButton(deleteButton, Color.RED);
        styleButton(clearAllButton, Color.ORANGE);
    }
    
    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
    }
    
    private void layoutComponents() {
        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Input panel (North)
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.add(new JLabel("Enter Task:"), BorderLayout.NORTH);
        inputPanel.add(taskInput, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);
        
        // List with scroll pane (Center)
        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Tasks"));
        
        // Button panel (South)
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearAllButton);
        
        // Add all panels to main panel
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    private void addEventListeners() {
        // Add button action
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });
        
        // Enter key in text field
        taskInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });
        
        // Delete button action
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedTask();
            }
        });
        
        // Clear all button action
        clearAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAllTasks();
            }
        });
        
        // Double-click to delete task
        taskList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    deleteSelectedTask();
                }
            }
        });
    }
    
    private void addTask() {
        String task = taskInput.getText().trim();
        
        if (!task.isEmpty()) {
            listModel.addElement(task);
            taskInput.setText("");
            taskInput.requestFocus();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Please enter a task!", 
                "Empty Task", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void deleteSelectedTask() {
        int selectedIndex = taskList.getSelectedIndex();
        
        if (selectedIndex != -1) {
            String task = listModel.getElementAt(selectedIndex);
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete: '" + task + "'?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                listModel.remove(selectedIndex);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "Please select a task to delete!",
                "No Task Selected",
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void clearAllTasks() {
        if (listModel.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "The task list is already empty!",
                "Empty List",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete ALL tasks?",
            "Confirm Clear All",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            listModel.clear();
        }
    }
}