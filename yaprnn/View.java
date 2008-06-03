package yaprnn.gui;


import javax.swing.tree.*;
import java.io.*;
import javax.swing.*;

class View extends javax.swing.JFrame {

    public View() {
    	initComponents();
    	this.setVisible(true);
    }
    private void initComponents() {

        popupmenuTreeNeuralNetwork = new javax.swing.JPopupMenu();
        pmenuitemClassify = new javax.swing.JMenuItem();
        pmenuitemTraining = new javax.swing.JMenuItem();
        pmenuitemSubsampling = new javax.swing.JMenuItem();
        pmenuitemResetNetwork = new javax.swing.JMenuItem();
        popupmenuButtonImport = new javax.swing.JPopupMenu();
        pmenuitemImportImage = new javax.swing.JMenuItem();
        pmenuitemImportAudio = new javax.swing.JMenuItem();
        dialogTraining = new javax.swing.JDialog();
        buttonTrain = new javax.swing.JButton();
        buttonResetTraining = new javax.swing.JButton();
        panelTestError = new javax.swing.JPanel();
        labelTestError = new javax.swing.JLabel();
        panelTrainingError = new javax.swing.JPanel();
        labelTrainingError = new javax.swing.JLabel();
        comboboxTrainingMethod = new javax.swing.JComboBox();
        labelLearningRate = new javax.swing.JLabel();
        spinnerLearningRate = new javax.swing.JSpinner();
        dialogClassify = new javax.swing.JDialog();
        buttonClassify = new javax.swing.JButton();
        panelClassificationData = new javax.swing.JPanel();
        labelClassificationData = new javax.swing.JLabel();
        panelClassificationResults = new javax.swing.JPanel();
        labelClassificationResults = new javax.swing.JLabel();
        labelClassificationResults1 = new javax.swing.JLabel();
        labelClassificationResults2 = new javax.swing.JLabel();
        labelClassificationResults3 = new javax.swing.JLabel();
        labelClassificationResults4 = new javax.swing.JLabel();
        labelClassificationResults5 = new javax.swing.JLabel();
        labelClassificationResults6 = new javax.swing.JLabel();
        dialogSubsampling = new javax.swing.JDialog();
        panelSubsamplingBefore1 = new javax.swing.JPanel();
        labelSubsamplingBefore = new javax.swing.JLabel();
        panelSubsamplingAfter = new javax.swing.JPanel();
        labelSubsamplingAfter = new javax.swing.JLabel();
        panelSubsamplingSettings1 = new javax.swing.JPanel();
        labelResolution = new javax.swing.JLabel();
        spinnerResolution = new javax.swing.JSpinner();
        labelOverlap = new javax.swing.JLabel();
        spinnerOverlap = new javax.swing.JSpinner();
        buttonProcessAll1 = new javax.swing.JButton();
        toolBar = new javax.swing.JToolBar();
        buttonNewMLP = new javax.swing.JButton();
        buttonLoadMLP = new javax.swing.JButton();
        buttonSaveMLP = new javax.swing.JButton();
        buttonLoadDataSet = new javax.swing.JButton();
        buttonSaveDataSet = new javax.swing.JButton();
        buttonImport = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        panelTreeNeuralNetwork = new javax.swing.JPanel();
        scrollpanelTreeNeuralNetwork = new javax.swing.JScrollPane();
        treeNeuralNetwork = new javax.swing.JTree();
        comboboxWeightInit = new javax.swing.JComboBox();
        labelWeightInit = new javax.swing.JLabel();
        buttonMLPEditable = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panelLayerDetails = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        labelCurrentLayer = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        panelDataSampleDetails = new javax.swing.JPanel();
        panelSubsamplingBefore3 = new javax.swing.JPanel();
        labelSubsamplingBefore1 = new javax.swing.JLabel();
        panelSubsamplingAfter1 = new javax.swing.JPanel();
        labelSubsamplingAfter1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuitemNewMLP = new javax.swing.JMenuItem();
        menuitemLoadMLP = new javax.swing.JMenuItem();
        menuitemSaveMLP = new javax.swing.JMenuItem();
        seperator1 = new javax.swing.JSeparator();
        menuitemLoadDataSet = new javax.swing.JMenuItem();
        menuitemSaveDataSet = new javax.swing.JMenuItem();
        separator2 = new javax.swing.JSeparator();
        menuImport = new javax.swing.JMenu();
        menuitemImportImage = new javax.swing.JMenuItem();
        menuitemImportAudio = new javax.swing.JMenuItem();
        seperator3 = new javax.swing.JSeparator();
        menuitemExit = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();
        Manual = new javax.swing.JMenuItem();
        menuBar1 = new javax.swing.JMenuBar();
        menuFile1 = new javax.swing.JMenu();
        menuitemNewMLP1 = new javax.swing.JMenuItem();
        menuitemLoadMLP1 = new javax.swing.JMenuItem();
        menuitemSaveMLP1 = new javax.swing.JMenuItem();
        seperator2 = new javax.swing.JSeparator();
        menuitemLoadDataSet1 = new javax.swing.JMenuItem();
        menuitemSaveDataSet1 = new javax.swing.JMenuItem();
        separator3 = new javax.swing.JSeparator();
        menuImport1 = new javax.swing.JMenu();
        menuitemImportImage1 = new javax.swing.JMenuItem();
        menuitemImportAudio1 = new javax.swing.JMenuItem();
        seperator4 = new javax.swing.JSeparator();
        menuitemExit1 = new javax.swing.JMenuItem();
        menuHelp1 = new javax.swing.JMenu();
        Manual1 = new javax.swing.JMenuItem();

        pmenuitemClassify.setIcon(new javax.swing.ImageIcon("iconClassify.png")); // NOI18N
        pmenuitemClassify.setText("Classify Data");
        pmenuitemClassify.setActionCommand("Set Layer #");
        pmenuitemClassify.setEnabled(false);
        pmenuitemClassify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
              //  pmenuitemClassifyActionPerformed(evt);
            }
        });
        popupmenuTreeNeuralNetwork.add(pmenuitemClassify);

        pmenuitemTraining.setIcon(new javax.swing.ImageIcon("iconTraining.png")); // NOI18N
        pmenuitemTraining.setText("Train MLP");
        pmenuitemTraining.setEnabled(false);
        pmenuitemTraining.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
             //   pmenuitemTrainingActionPerformed(evt);
            }
        });
        popupmenuTreeNeuralNetwork.add(pmenuitemTraining);

        pmenuitemSubsampling.setIcon(new javax.swing.ImageIcon("iconProcessAll.png")); // NOI18N
        pmenuitemSubsampling.setText("Subsampling");
        pmenuitemSubsampling.setEnabled(false);
        pmenuitemSubsampling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            //    pmenuitemSubsamplingActionPerformed(evt);
            }
        });
        popupmenuTreeNeuralNetwork.add(pmenuitemSubsampling);

        pmenuitemResetNetwork.setIcon(new javax.swing.ImageIcon("iconReset.png")); // NOI18N
        pmenuitemResetNetwork.setText("Reset Network");
        pmenuitemResetNetwork.setEnabled(false);
        pmenuitemResetNetwork.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
             //   pmenuitemResetNetworkActionPerformed(evt);
            }
        });
        popupmenuTreeNeuralNetwork.add(pmenuitemResetNetwork);

        pmenuitemImportImage.setIcon(new javax.swing.ImageIcon("iconLoadImage.png")); // NOI18N
        pmenuitemImportImage.setText("Import Image Data");
        pmenuitemImportImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
              //  pmenuitemImportImageActionPerformed(evt);
            }
        });
        popupmenuButtonImport.add(pmenuitemImportImage);

        pmenuitemImportAudio.setIcon(new javax.swing.ImageIcon("iconLoadAudio.png")); // NOI18N
        pmenuitemImportAudio.setText("Import Audio Data");
        pmenuitemImportAudio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
             //   pmenuitemImportAudioActionPerformed(evt);
            }
        });
        popupmenuButtonImport.add(pmenuitemImportAudio);

        dialogTraining.setResizable(false);

        buttonTrain.setIcon(new javax.swing.ImageIcon("iconTraining.png")); // NOI18N
        buttonTrain.setText("TRAIN");

        buttonResetTraining.setIcon(new javax.swing.ImageIcon("iconReset.png")); // NOI18N
        buttonResetTraining.setText("Reset");

        panelTestError.setBorder(javax.swing.BorderFactory.createTitledBorder("Test Error"));

        labelTestError.setIcon(new javax.swing.ImageIcon("testError.png")); // NOI18N

        javax.swing.GroupLayout panelTestErrorLayout = new javax.swing.GroupLayout(panelTestError);
        panelTestError.setLayout(panelTestErrorLayout);
        panelTestErrorLayout.setHorizontalGroup(
            panelTestErrorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTestErrorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelTestError)
                .addContainerGap())
        );
        panelTestErrorLayout.setVerticalGroup(
            panelTestErrorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTestErrorLayout.createSequentialGroup()
                .addComponent(labelTestError)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelTrainingError.setBorder(javax.swing.BorderFactory.createTitledBorder("Training Error"));

        labelTrainingError.setIcon(new javax.swing.ImageIcon("trainingError.png")); // NOI18N

        javax.swing.GroupLayout panelTrainingErrorLayout = new javax.swing.GroupLayout(panelTrainingError);
        panelTrainingError.setLayout(panelTrainingErrorLayout);
        panelTrainingErrorLayout.setHorizontalGroup(
            panelTrainingErrorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTrainingErrorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTrainingError)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelTrainingErrorLayout.setVerticalGroup(
            panelTrainingErrorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTrainingErrorLayout.createSequentialGroup()
                .addComponent(labelTrainingError)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        comboboxTrainingMethod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Online learning", "Batch learning" }));

        labelLearningRate.setText("Learning rate Eta");

        javax.swing.GroupLayout dialogTrainingLayout = new javax.swing.GroupLayout(dialogTraining.getContentPane());
        dialogTraining.getContentPane().setLayout(dialogTrainingLayout);
        dialogTrainingLayout.setHorizontalGroup(
            dialogTrainingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogTrainingLayout.createSequentialGroup()
                .addGroup(dialogTrainingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTrainingError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelTestError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(dialogTrainingLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(comboboxTrainingMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(labelLearningRate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerLearningRate, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75)
                        .addComponent(buttonTrain)
                        .addGap(18, 18, 18)
                        .addComponent(buttonResetTraining, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        dialogTrainingLayout.setVerticalGroup(
            dialogTrainingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogTrainingLayout.createSequentialGroup()
                .addComponent(panelTrainingError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTestError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dialogTrainingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogTrainingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(buttonResetTraining, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonTrain, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dialogTrainingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comboboxTrainingMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelLearningRate)
                        .addComponent(spinnerLearningRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dialogClassify.setTitle("Classification");
        dialogClassify.setBackground(java.awt.Color.white);
        dialogClassify.setResizable(false);
        dialogClassify.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
//                dialogClassifyWindowActivated(evt);
            }
        });

        buttonClassify.setIcon(new javax.swing.ImageIcon("iconClassify.png")); // NOI18N
        buttonClassify.setMnemonic('C');
        buttonClassify.setText("Classify");
        buttonClassify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                buttonClassifyActionPerformed(evt);
            }
        });

        panelClassificationData.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        labelClassificationData.setIcon(new javax.swing.ImageIcon("5.png")); // NOI18N

        javax.swing.GroupLayout panelClassificationDataLayout = new javax.swing.GroupLayout(panelClassificationData);
        panelClassificationData.setLayout(panelClassificationDataLayout);
        panelClassificationDataLayout.setHorizontalGroup(
            panelClassificationDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelClassificationData)
        );
        panelClassificationDataLayout.setVerticalGroup(
            panelClassificationDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelClassificationData)
        );

        labelClassificationResults.setText("Classification Results:");

        labelClassificationResults1.setText("1: 1.3%                                   6: 5.1%");

        labelClassificationResults2.setText("2: 1.2%                                   7: 0.9%\n");

        labelClassificationResults3.setText("3: 3.7%                                   8: 3.0%\n");

        labelClassificationResults4.setText("4: 2.7%                                   9: 2.1%\n");

        labelClassificationResults5.setText("5: 79.6%                                 0: 0.4% ");

        labelClassificationResults6.setText("Data sample classified as '5'");

        javax.swing.GroupLayout panelClassificationResultsLayout = new javax.swing.GroupLayout(panelClassificationResults);
        panelClassificationResults.setLayout(panelClassificationResultsLayout);
        panelClassificationResultsLayout.setHorizontalGroup(
            panelClassificationResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClassificationResultsLayout.createSequentialGroup()
                .addGroup(panelClassificationResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelClassificationResults4)
                    .addComponent(labelClassificationResults1)
                    .addComponent(labelClassificationResults)
                    .addComponent(labelClassificationResults2)
                    .addComponent(labelClassificationResults3)
                    .addComponent(labelClassificationResults5)
                    .addComponent(labelClassificationResults6))
                .addContainerGap(5, Short.MAX_VALUE))
        );
        panelClassificationResultsLayout.setVerticalGroup(
            panelClassificationResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClassificationResultsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelClassificationResults)
                .addGap(7, 7, 7)
                .addComponent(labelClassificationResults1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelClassificationResults2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelClassificationResults3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelClassificationResults4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelClassificationResults5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(labelClassificationResults6, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout dialogClassifyLayout = new javax.swing.GroupLayout(dialogClassify.getContentPane());
        dialogClassify.getContentPane().setLayout(dialogClassifyLayout);
        dialogClassifyLayout.setHorizontalGroup(
            dialogClassifyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogClassifyLayout.createSequentialGroup()
                .addGroup(dialogClassifyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogClassifyLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(panelClassificationData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogClassifyLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(panelClassificationResults, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE))
                    .addGroup(dialogClassifyLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(buttonClassify)))
                .addContainerGap())
        );
        dialogClassifyLayout.setVerticalGroup(
            dialogClassifyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogClassifyLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelClassificationData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelClassificationResults, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonClassify)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dialogSubsampling.setTitle("Subsampling");

        panelSubsamplingBefore1.setBorder(javax.swing.BorderFactory.createTitledBorder("Before"));

        labelSubsamplingBefore.setIcon(new javax.swing.ImageIcon("5.png")); // NOI18N

        javax.swing.GroupLayout panelSubsamplingBefore1Layout = new javax.swing.GroupLayout(panelSubsamplingBefore1);
        panelSubsamplingBefore1.setLayout(panelSubsamplingBefore1Layout);
        panelSubsamplingBefore1Layout.setHorizontalGroup(
            panelSubsamplingBefore1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSubsamplingBefore1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelSubsamplingBefore)
                .addContainerGap())
        );
        panelSubsamplingBefore1Layout.setVerticalGroup(
            panelSubsamplingBefore1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSubsamplingBefore1Layout.createSequentialGroup()
                .addComponent(labelSubsamplingBefore)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelSubsamplingAfter.setBorder(javax.swing.BorderFactory.createTitledBorder("After"));

        labelSubsamplingAfter.setIcon(new javax.swing.ImageIcon("5s.png")); // NOI18N

        javax.swing.GroupLayout panelSubsamplingAfterLayout = new javax.swing.GroupLayout(panelSubsamplingAfter);
        panelSubsamplingAfter.setLayout(panelSubsamplingAfterLayout);
        panelSubsamplingAfterLayout.setHorizontalGroup(
            panelSubsamplingAfterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSubsamplingAfterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelSubsamplingAfter)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelSubsamplingAfterLayout.setVerticalGroup(
            panelSubsamplingAfterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSubsamplingAfterLayout.createSequentialGroup()
                .addComponent(labelSubsamplingAfter)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelSubsamplingSettings1.setBorder(javax.swing.BorderFactory.createTitledBorder("Settings"));

        labelResolution.setText("Resolution");

        labelOverlap.setText("Overlap %");

        javax.swing.GroupLayout panelSubsamplingSettings1Layout = new javax.swing.GroupLayout(panelSubsamplingSettings1);
        panelSubsamplingSettings1.setLayout(panelSubsamplingSettings1Layout);
        panelSubsamplingSettings1Layout.setHorizontalGroup(
            panelSubsamplingSettings1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSubsamplingSettings1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(labelResolution)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spinnerResolution, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addComponent(labelOverlap)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spinnerOverlap, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );
        panelSubsamplingSettings1Layout.setVerticalGroup(
            panelSubsamplingSettings1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSubsamplingSettings1Layout.createSequentialGroup()
                .addGroup(panelSubsamplingSettings1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelResolution)
                    .addComponent(spinnerResolution, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelOverlap)
                    .addComponent(spinnerOverlap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonProcessAll1.setIcon(new javax.swing.ImageIcon("iconProcessAll.png")); // NOI18N
        buttonProcessAll1.setText("PROCESS ALL");
        buttonProcessAll1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
             //   buttonProcessAll1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dialogSubsamplingLayout = new javax.swing.GroupLayout(dialogSubsampling.getContentPane());
        dialogSubsampling.getContentPane().setLayout(dialogSubsamplingLayout);
        dialogSubsamplingLayout.setHorizontalGroup(
            dialogSubsamplingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogSubsamplingLayout.createSequentialGroup()
                .addGroup(dialogSubsamplingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogSubsamplingLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(panelSubsamplingBefore1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelSubsamplingAfter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dialogSubsamplingLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(panelSubsamplingSettings1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dialogSubsamplingLayout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(buttonProcessAll1)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        dialogSubsamplingLayout.setVerticalGroup(
            dialogSubsamplingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogSubsamplingLayout.createSequentialGroup()
                .addGroup(dialogSubsamplingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelSubsamplingAfter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelSubsamplingBefore1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelSubsamplingSettings1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonProcessAll1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("YAPRNN - GUI PROTOTYPE");

        toolBar.setFloatable(false);
        toolBar.setRollover(true);

        buttonNewMLP.setIcon(new javax.swing.ImageIcon("iconNewMLP.png")); // NOI18N
        buttonNewMLP.setToolTipText("New MLP");
        buttonNewMLP.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), null));
        buttonNewMLP.setPreferredSize(new java.awt.Dimension(45, 45));
        buttonNewMLP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
          //      buttonNewMLPActionPerformed(evt);
            }
        });
        toolBar.add(buttonNewMLP);

        buttonLoadMLP.setIcon(new javax.swing.ImageIcon("iconLoadMLP.png")); // NOI18N
        buttonLoadMLP.setToolTipText("Load MLP");
        buttonLoadMLP.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), null));
        buttonLoadMLP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
           //     buttonLoadMLPActionPerformed(evt);
            }
        });
        toolBar.add(buttonLoadMLP);

        buttonSaveMLP.setIcon(new javax.swing.ImageIcon("iconSaveMLP.png")); // NOI18N
        buttonSaveMLP.setToolTipText("Save MLP");
        buttonSaveMLP.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), null));
        buttonSaveMLP.setPreferredSize(new java.awt.Dimension(45, 45));
        buttonSaveMLP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
          //      buttonSaveMLPActionPerformed(evt);
            }
        });
        toolBar.add(buttonSaveMLP);

        buttonLoadDataSet.setIcon(new javax.swing.ImageIcon("iconLoadDataSet.png")); // NOI18N
        buttonLoadDataSet.setToolTipText("Load Data Set");
        buttonLoadDataSet.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), null));
        buttonLoadDataSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
         //       buttonLoadDataSetActionPerformed(evt);
            }
        });
        toolBar.add(buttonLoadDataSet);

        buttonSaveDataSet.setIcon(new javax.swing.ImageIcon("iconSaveDataSet.png")); // NOI18N
        buttonSaveDataSet.setToolTipText("Save Data Set");
        buttonSaveDataSet.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), null));
        buttonSaveDataSet.setPreferredSize(new java.awt.Dimension(45, 45));
        buttonSaveDataSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
          //      buttonSaveDataSetActionPerformed(evt);
            }
        });
        toolBar.add(buttonSaveDataSet);

        buttonImport.setIcon(new javax.swing.ImageIcon("iconLoadAudioImage.png")); // NOI18N
        buttonImport.setToolTipText("Import Audio / Image Data");
        buttonImport.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), null));
        buttonImport.setFocusable(false);
        buttonImport.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonImport.setPreferredSize(new java.awt.Dimension(45, 45));
        buttonImport.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
        //        buttonImportActionPerformed(evt);
            }
        });
        toolBar.add(buttonImport);

        panelTreeNeuralNetwork.setBorder(javax.swing.BorderFactory.createTitledBorder("Neural Network"));

        treeNeuralNetwork.setComponentPopupMenu(popupmenuTreeNeuralNetwork);
        treeNeuralNetwork.setModel(null);
        treeNeuralNetwork.setRootVisible(false);
        treeNeuralNetwork.setShowsRootHandles(true);
        treeNeuralNetwork.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
//                treeNeuralNetworkMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
 //               treeNeuralNetworkMouseReleased(evt);
            }
        });
        scrollpanelTreeNeuralNetwork.setViewportView(treeNeuralNetwork);

        comboboxWeightInit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Autoencoder", "Random" }));
        comboboxWeightInit.setEnabled(false);

        labelWeightInit.setText("Weight Initialization:");

        buttonMLPEditable.setIcon(new javax.swing.ImageIcon("iconEditable.png")); // NOI18N
        buttonMLPEditable.setBorder(null);
        buttonMLPEditable.setBorderPainted(false);
        buttonMLPEditable.setContentAreaFilled(false);
        buttonMLPEditable.setDisabledIcon(new javax.swing.ImageIcon("iconNotEditable.png")); // NOI18N
        buttonMLPEditable.setEnabled(false);
        buttonMLPEditable.setFocusPainted(false);
        buttonMLPEditable.setFocusable(false);

        javax.swing.GroupLayout panelTreeNeuralNetworkLayout = new javax.swing.GroupLayout(panelTreeNeuralNetwork);
        panelTreeNeuralNetwork.setLayout(panelTreeNeuralNetworkLayout);
        panelTreeNeuralNetworkLayout.setHorizontalGroup(
            panelTreeNeuralNetworkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTreeNeuralNetworkLayout.createSequentialGroup()
                .addComponent(buttonMLPEditable, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelWeightInit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboboxWeightInit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(scrollpanelTreeNeuralNetwork, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
        );
        panelTreeNeuralNetworkLayout.setVerticalGroup(
            panelTreeNeuralNetworkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTreeNeuralNetworkLayout.createSequentialGroup()
                .addGroup(panelTreeNeuralNetworkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTreeNeuralNetworkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelWeightInit)
                        .addComponent(comboboxWeightInit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(buttonMLPEditable, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollpanelTreeNeuralNetwork, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(panelTreeNeuralNetwork);

        panelLayerDetails.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {new Double(0.31), new Double(0.4), new Double(0.8), new Double(0.25), new Double(0.13)},
                {new Double(0.7), new Double(0.5), new Double(0.67), new Double(0.5), new Double(0.2)},
                {new Double(0.51), new Double(0.7), new Double(0.123), new Double(0.234), new Double(0.34)}
            },
            new String [] {
                "Neuron1", "Neuron2", "Neuron3", "Neuron4", "Neuron5"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        labelCurrentLayer.setText("Current layer's weights:");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {new Double(0.31), new Double(0.4), new Double(0.8), new Double(0.25), new Double(0.13)},
                {new Double(0.7), new Double(0.5), new Double(0.67), new Double(0.5), new Double(0.2)},
                {new Double(0.51), new Double(0.7), new Double(0.123), new Double(0.234), new Double(0.34)}
            },
            new String [] {
                "Neuron5", "Neuron6", "Neuron7", "Neuron8", "Neuron9"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jLabel4.setText("Additional Information:");

        javax.swing.GroupLayout panelLayerDetailsLayout = new javax.swing.GroupLayout(panelLayerDetails);
        panelLayerDetails.setLayout(panelLayerDetailsLayout);
        panelLayerDetailsLayout.setHorizontalGroup(
            panelLayerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayerDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelCurrentLayer)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(144, Short.MAX_VALUE))
        );
        panelLayerDetailsLayout.setVerticalGroup(
            panelLayerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayerDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelCurrentLayer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jLabel4)
                .addContainerGap(135, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Layer Details", panelLayerDetails);

        panelDataSampleDetails.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        panelSubsamplingBefore3.setBorder(javax.swing.BorderFactory.createTitledBorder("Original Data"));

        labelSubsamplingBefore1.setIcon(new javax.swing.ImageIcon("5.png")); // NOI18N

        javax.swing.GroupLayout panelSubsamplingBefore3Layout = new javax.swing.GroupLayout(panelSubsamplingBefore3);
        panelSubsamplingBefore3.setLayout(panelSubsamplingBefore3Layout);
        panelSubsamplingBefore3Layout.setHorizontalGroup(
            panelSubsamplingBefore3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSubsamplingBefore3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelSubsamplingBefore1)
                .addContainerGap())
        );
        panelSubsamplingBefore3Layout.setVerticalGroup(
            panelSubsamplingBefore3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSubsamplingBefore3Layout.createSequentialGroup()
                .addComponent(labelSubsamplingBefore1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelSubsamplingAfter1.setBorder(javax.swing.BorderFactory.createTitledBorder("After"));

        labelSubsamplingAfter1.setIcon(new javax.swing.ImageIcon("5s.png")); // NOI18N

        javax.swing.GroupLayout panelSubsamplingAfter1Layout = new javax.swing.GroupLayout(panelSubsamplingAfter1);
        panelSubsamplingAfter1.setLayout(panelSubsamplingAfter1Layout);
        panelSubsamplingAfter1Layout.setHorizontalGroup(
            panelSubsamplingAfter1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSubsamplingAfter1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelSubsamplingAfter1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelSubsamplingAfter1Layout.setVerticalGroup(
            panelSubsamplingAfter1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSubsamplingAfter1Layout.createSequentialGroup()
                .addComponent(labelSubsamplingAfter1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setText("Filename:");

        jLabel2.setText("Data Sample label:");

        jLabel3.setText("Used subsampling options:");

        javax.swing.GroupLayout panelDataSampleDetailsLayout = new javax.swing.GroupLayout(panelDataSampleDetails);
        panelDataSampleDetails.setLayout(panelDataSampleDetailsLayout);
        panelDataSampleDetailsLayout.setHorizontalGroup(
            panelDataSampleDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDataSampleDetailsLayout.createSequentialGroup()
                .addGroup(panelDataSampleDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDataSampleDetailsLayout.createSequentialGroup()
                        .addComponent(panelSubsamplingBefore3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelSubsamplingAfter1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDataSampleDetailsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelDataSampleDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDataSampleDetailsLayout.setVerticalGroup(
            panelDataSampleDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDataSampleDetailsLayout.createSequentialGroup()
                .addGroup(panelDataSampleDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelSubsamplingBefore3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelSubsamplingAfter1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addContainerGap(99, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Data Sample Details", panelDataSampleDetails);

        jSplitPane1.setRightComponent(jTabbedPane1);

        menuFile.setText("File");

        menuitemNewMLP.setIcon(new javax.swing.ImageIcon("iconNewMLP.png")); // NOI18N
        menuitemNewMLP.setMnemonic('N');
        menuitemNewMLP.setText("New MLP");
        menuitemNewMLP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
          //      menuitemNewMLPActionPerformed(evt);
            }
        });
        menuFile.add(menuitemNewMLP);

        menuitemLoadMLP.setIcon(new javax.swing.ImageIcon("iconLoadMLP.png")); // NOI18N
        menuitemLoadMLP.setMnemonic('L');
        menuitemLoadMLP.setText("Load MLP");
        menuitemLoadMLP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
        //        menuitemLoadMLPActionPerformed(evt);
            }
        });
        menuFile.add(menuitemLoadMLP);

        menuitemSaveMLP.setIcon(new javax.swing.ImageIcon("iconSaveMLP.png")); // NOI18N
        menuitemSaveMLP.setMnemonic('S');
        menuitemSaveMLP.setText("Save MLP");
        menuitemSaveMLP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
       //         menuitemSaveMLPActionPerformed(evt);
            }
        });
        menuFile.add(menuitemSaveMLP);
        menuFile.add(seperator1);

        menuitemLoadDataSet.setIcon(new javax.swing.ImageIcon("iconLoadDataSet.png")); // NOI18N
        menuitemLoadDataSet.setMnemonic('D');
        menuitemLoadDataSet.setText("Load Data Set");
        menuFile.add(menuitemLoadDataSet);

        menuitemSaveDataSet.setIcon(new javax.swing.ImageIcon("iconSaveDataSet.png")); // NOI18N
        menuitemSaveDataSet.setMnemonic('V');
        menuitemSaveDataSet.setText("Save Data Set");
        menuFile.add(menuitemSaveDataSet);
        menuFile.add(separator2);

        menuImport.setIcon(new javax.swing.ImageIcon("iconLoadAudioImage.png")); // NOI18N
        menuImport.setText("Import");

        menuitemImportImage.setIcon(new javax.swing.ImageIcon("iconLoadImage.png")); // NOI18N
        menuitemImportImage.setText("Image Data");
        menuitemImportImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
         //       menuitemImportImageActionPerformed(evt);
            }
        });
        menuImport.add(menuitemImportImage);

        menuitemImportAudio.setIcon(new javax.swing.ImageIcon("iconLoadAudio.png")); // NOI18N
        menuitemImportAudio.setText("Audio Data");
        menuitemImportAudio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                menuitemImportAudioActionPerformed(evt);
            }
        });
        menuImport.add(menuitemImportAudio);

        menuFile.add(menuImport);
        menuFile.add(seperator3);

        menuitemExit.setIcon(new javax.swing.ImageIcon("iconExit.png")); // NOI18N
        menuitemExit.setMnemonic('x');
        menuitemExit.setText("Exit");
        menuitemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
        //        menuitemExitActionPerformed(evt);
            }
        });
        menuFile.add(menuitemExit);

        menuBar.add(menuFile);

        menuHelp.setText("Help");

        Manual.setText("Manual");
        menuHelp.add(Manual);

        menuBar.add(menuHelp);

        menuFile1.setText("File");

        menuitemNewMLP1.setIcon(new javax.swing.ImageIcon("iconNewMLP.png")); // NOI18N
        menuitemNewMLP1.setMnemonic('N');
        menuitemNewMLP1.setText("New MLP");
        menuitemNewMLP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
        //        menuitemNewMLPActionPerformed(evt);
            }
        });
        menuFile1.add(menuitemNewMLP1);

        menuitemLoadMLP1.setIcon(new javax.swing.ImageIcon("iconLoadMLP.png")); // NOI18N
        menuitemLoadMLP1.setMnemonic('L');
        menuitemLoadMLP1.setText("Load MLP");
        menuitemLoadMLP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                menuitemLoadMLPActionPerformed(evt);
            }
        });
        menuFile1.add(menuitemLoadMLP1);

        menuitemSaveMLP1.setIcon(new javax.swing.ImageIcon("iconSaveMLP.png")); // NOI18N
        menuitemSaveMLP1.setMnemonic('S');
        menuitemSaveMLP1.setText("Save MLP");
        menuitemSaveMLP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
        //        menuitemSaveMLPActionPerformed(evt);
            }
        });
        menuFile1.add(menuitemSaveMLP1);
        menuFile1.add(seperator2);

        menuitemLoadDataSet1.setIcon(new javax.swing.ImageIcon("iconLoadDataSet.png")); // NOI18N
        menuitemLoadDataSet1.setMnemonic('D');
        menuitemLoadDataSet1.setText("Load Data Set");
        menuFile1.add(menuitemLoadDataSet1);

        menuitemSaveDataSet1.setIcon(new javax.swing.ImageIcon("iconSaveDataSet.png")); // NOI18N
        menuitemSaveDataSet1.setMnemonic('V');
        menuitemSaveDataSet1.setText("Save Data Set");
        menuFile1.add(menuitemSaveDataSet1);
        menuFile1.add(separator3);

        menuImport1.setIcon(new javax.swing.ImageIcon("iconLoadAudioImage.png")); // NOI18N
        menuImport1.setText("Import");

        menuitemImportImage1.setIcon(new javax.swing.ImageIcon("iconLoadImage.png")); // NOI18N
        menuitemImportImage1.setText("Image Data");
        menuitemImportImage1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
         //       menuitemImportImageActionPerformed(evt);
            }
        });
        menuImport1.add(menuitemImportImage1);

        menuitemImportAudio1.setIcon(new javax.swing.ImageIcon("iconLoadAudio.png")); // NOI18N
        menuitemImportAudio1.setText("Audio Data");
        menuitemImportAudio1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
         //       menuitemImportAudioActionPerformed(evt);
            }
        });
        menuImport1.add(menuitemImportAudio1);

        menuFile1.add(menuImport1);
        menuFile1.add(seperator4);

        menuitemExit1.setIcon(new javax.swing.ImageIcon("iconExit.png")); // NOI18N
        menuitemExit1.setMnemonic('x');
        menuitemExit1.setText("Exit");
        menuitemExit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
        //        menuitemExitActionPerformed(evt);
            }
        });
        menuFile1.add(menuitemExit1);

        menuBar1.add(menuFile1);

        menuHelp1.setText("Help");

        Manual1.setText("Manual");
        menuHelp1.add(Manual1);

        menuBar1.add(menuHelp1);

        setJMenuBar(menuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(toolBar, javax.swing.GroupLayout.DEFAULT_SIZE, 685, Short.MAX_VALUE)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Manual;
    private javax.swing.JMenuItem Manual1;
    private javax.swing.JButton buttonClassify;
    private javax.swing.JButton buttonImport;
    private javax.swing.JButton buttonLoadDataSet;
    private javax.swing.JButton buttonLoadMLP;
    private javax.swing.JButton buttonMLPEditable;
    private javax.swing.JButton buttonNewMLP;
    private javax.swing.JButton buttonProcessAll1;
    private javax.swing.JButton buttonResetTraining;
    private javax.swing.JButton buttonSaveDataSet;
    private javax.swing.JButton buttonSaveMLP;
    private javax.swing.JButton buttonTrain;
    private javax.swing.JComboBox comboboxTrainingMethod;
    private javax.swing.JComboBox comboboxWeightInit;
    private javax.swing.JDialog dialogClassify;
    private javax.swing.JDialog dialogSubsampling;
    private javax.swing.JDialog dialogTraining;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel labelClassificationData;
    private javax.swing.JLabel labelClassificationResults;
    private javax.swing.JLabel labelClassificationResults1;
    private javax.swing.JLabel labelClassificationResults2;
    private javax.swing.JLabel labelClassificationResults3;
    private javax.swing.JLabel labelClassificationResults4;
    private javax.swing.JLabel labelClassificationResults5;
    private javax.swing.JLabel labelClassificationResults6;
    private javax.swing.JLabel labelCurrentLayer;
    private javax.swing.JLabel labelLearningRate;
    private javax.swing.JLabel labelOverlap;
    private javax.swing.JLabel labelResolution;
    private javax.swing.JLabel labelSubsamplingAfter;
    private javax.swing.JLabel labelSubsamplingAfter1;
    private javax.swing.JLabel labelSubsamplingBefore;
    private javax.swing.JLabel labelSubsamplingBefore1;
    private javax.swing.JLabel labelTestError;
    private javax.swing.JLabel labelTrainingError;
    private javax.swing.JLabel labelWeightInit;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuBar menuBar1;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuFile1;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenu menuHelp1;
    private javax.swing.JMenu menuImport;
    private javax.swing.JMenu menuImport1;
    private javax.swing.JMenuItem menuitemExit;
    private javax.swing.JMenuItem menuitemExit1;
    private javax.swing.JMenuItem menuitemImportAudio;
    private javax.swing.JMenuItem menuitemImportAudio1;
    private javax.swing.JMenuItem menuitemImportImage;
    private javax.swing.JMenuItem menuitemImportImage1;
    private javax.swing.JMenuItem menuitemLoadDataSet;
    private javax.swing.JMenuItem menuitemLoadDataSet1;
    private javax.swing.JMenuItem menuitemLoadMLP;
    private javax.swing.JMenuItem menuitemLoadMLP1;
    private javax.swing.JMenuItem menuitemNewMLP;
    private javax.swing.JMenuItem menuitemNewMLP1;
    private javax.swing.JMenuItem menuitemSaveDataSet;
    private javax.swing.JMenuItem menuitemSaveDataSet1;
    private javax.swing.JMenuItem menuitemSaveMLP;
    private javax.swing.JMenuItem menuitemSaveMLP1;
    private javax.swing.JPanel panelClassificationData;
    private javax.swing.JPanel panelClassificationResults;
    private javax.swing.JPanel panelDataSampleDetails;
    private javax.swing.JPanel panelLayerDetails;
    private javax.swing.JPanel panelSubsamplingAfter;
    private javax.swing.JPanel panelSubsamplingAfter1;
    private javax.swing.JPanel panelSubsamplingBefore1;
    private javax.swing.JPanel panelSubsamplingBefore3;
    private javax.swing.JPanel panelSubsamplingSettings1;
    private javax.swing.JPanel panelTestError;
    private javax.swing.JPanel panelTrainingError;
    private javax.swing.JPanel panelTreeNeuralNetwork;
    private javax.swing.JMenuItem pmenuitemClassify;
    private javax.swing.JMenuItem pmenuitemImportAudio;
    private javax.swing.JMenuItem pmenuitemImportImage;
    private javax.swing.JMenuItem pmenuitemResetNetwork;
    private javax.swing.JMenuItem pmenuitemSubsampling;
    private javax.swing.JMenuItem pmenuitemTraining;
    private javax.swing.JPopupMenu popupmenuButtonImport;
    private javax.swing.JPopupMenu popupmenuTreeNeuralNetwork;
    private javax.swing.JScrollPane scrollpanelTreeNeuralNetwork;
    private javax.swing.JSeparator separator2;
    private javax.swing.JSeparator separator3;
    private javax.swing.JSeparator seperator1;
    private javax.swing.JSeparator seperator2;
    private javax.swing.JSeparator seperator3;
    private javax.swing.JSeparator seperator4;
    private javax.swing.JSpinner spinnerLearningRate;
    private javax.swing.JSpinner spinnerOverlap;
    private javax.swing.JSpinner spinnerResolution;
    private javax.swing.JToolBar toolBar;
    private javax.swing.JTree treeNeuralNetwork;
    // End of variables declaration//GEN-END:variables
}