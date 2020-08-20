import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SplitMerge extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textFieldMerge;
	private JFileChooser filechooser;
	private File file,folder;
	private File[] files;
	private JComboBox<String> partition;
	private OutputStream os = null;
	private int sizeCopied=0,percentage=0;
	private JProgressBar pb;
	private JLabel stateLbl;
	private JButton browseBtn;
	private JButton mergeButton;
	private JProgressBar mergePB;
	private JLabel lblStatusMerge;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SplitMerge.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SplitMerge frame = new SplitMerge();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SplitMerge() {
		setResizable(false);
		setTitle("File Splitter & Merger");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Split", null, panel, "Split File");
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Select File :");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(29, 36, 74, 14);
		panel.add(lblNewLabel);
		
		filechooser=new JFileChooser();
		filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(113, 33, 202, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton browseButton = new JButton("Browse");
		browseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				btnActionPerformed(e);
			}
		});
		browseButton.setBounds(325, 33, 67, 20);
		panel.add(browseButton);
		
		pb = new JProgressBar();
		pb.setStringPainted(true);
		pb.setBounds(29, 165, 363, 14);
		panel.add(pb);
		
		JButton splitButton = new JButton("Split");
		splitButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		splitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				splitActionPerformed(e);
			}
		});
		splitButton.setBounds(290, 86, 102, 31);
		panel.add(splitButton);
		
		JLabel lblNewLabel_2_1 = new JLabel("Status:");
		lblNewLabel_2_1.setBounds(29, 140, 46, 14);
		panel.add(lblNewLabel_2_1);
		
		stateLbl = new JLabel("");
		stateLbl.setBounds(73, 140, 131, 14);
		panel.add(stateLbl);
		
		JLabel lblNewLabel_1_1 = new JLabel("select file (minimum size > 10 MB)");
		lblNewLabel_1_1.setFont(new Font("Calibri", Font.ITALIC, 10));
		lblNewLabel_1_1.setBounds(29, 61, 278, 14);
		panel.add(lblNewLabel_1_1);
		
		partition = new JComboBox<String>();
		partition.setModel(new DefaultComboBoxModel<String>(new String[] {"Select", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		partition.setBounds(168, 91, 64, 22);
		panel.add(partition);
		
		JLabel lblNewLabel_4 = new JLabel("Partitions:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_4.setBounds(91, 94, 67, 14);
		panel.add(lblNewLabel_4);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		tabbedPane.addTab("Merge", null, panel_2, null);
		
		JLabel lblSelectFolder = new JLabel("Select Folder :");
		lblSelectFolder.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSelectFolder.setBounds(29, 36, 105, 14);
		panel_2.add(lblSelectFolder);
		
		textFieldMerge = new JTextField();
		textFieldMerge.setEditable(false);
		textFieldMerge.setColumns(10);
		textFieldMerge.setBounds(128, 34, 179, 20);
		panel_2.add(textFieldMerge);
		
		browseBtn = new JButton("Browse");
		browseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				browseAction(e);
			}

		});
		browseBtn.setBounds(325, 33, 67, 23);
		panel_2.add(browseBtn);
		
		mergePB = new JProgressBar();
		mergePB.setBounds(29, 165, 363, 14);
		panel_2.add(mergePB);
		
		mergeButton = new JButton("Merge");
		mergeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mergeActionPerformed(e);
			}
		});
		mergeButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		mergeButton.setBounds(158, 93, 105, 30);
		panel_2.add(mergeButton);
		
		JLabel lblNewLabel_1 = new JLabel("select folder that contains all the parts of the file.");
		lblNewLabel_1.setFont(new Font("Calibri", Font.ITALIC, 10));
		lblNewLabel_1.setBounds(29, 61, 278, 14);
		panel_2.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Status:");
		lblNewLabel_2.setBounds(29, 140, 46, 14);
		panel_2.add(lblNewLabel_2);
		
		lblStatusMerge = new JLabel("");
		lblStatusMerge.setBounds(74, 140, 131, 14);
		panel_2.add(lblStatusMerge);
	}
	
	private void btnActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int ret=filechooser.showOpenDialog(this);
        if(ret==JFileChooser.APPROVE_OPTION)
        {
            file=filechooser.getSelectedFile();
            textField.setText(file.getAbsolutePath());
        }
	}
	
	private void browseAction(ActionEvent e) {
		// TODO Auto-generated method stub
		filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int ret=filechooser.showOpenDialog(this);
        if(ret==JFileChooser.APPROVE_OPTION)
        {
            folder=filechooser.getSelectedFile();
            FilenameFilter filter=new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String name) {
					// TODO Auto-generated method stub
					if(name.matches(".*part\\d+")) return true;
					else return false;
				}
			};
            files=folder.listFiles(filter);
            Arrays.sort(files);
            textFieldMerge.setText(folder.getAbsolutePath());
        }
	}
	
	private void splitActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	if( file!=null && file.exists()) {
		if(checkFileSize(file)) {
			String parts=(String) partition.getSelectedItem();
			if(!parts.equals("Select")) {
				int part=Integer.parseInt(parts);
				Thread t=new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							SplitFile(part);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				t.start();
			}
			else {
				JOptionPane.showMessageDialog(this,"Select partitions first.","Alert",JOptionPane.WARNING_MESSAGE);
			}
		}
		else {
			JOptionPane.showMessageDialog(this,"<html><p align='center'>File size is smaller then expected!<br>Minimum size is 10 MB.</p></html>","Alert",JOptionPane.WARNING_MESSAGE);
		}
	}
		
	}
	
	// to validate file size
	private boolean checkFileSize(File f) {
		if(f.length()>=10*1024*1024) {
			return true;
		}
		return false;
	}
	
	// merge button action
	private void mergeActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(files.length>0) {
			new Thread(new Runnable() {
				
					@Override
					public void run() {
					// TODO Auto-generated method stub
					try {
						MergeFile();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
			}).start();
		}
	}
	
	// merge file function
	private void MergeFile() throws Exception {
		long totalSize=0;
		os=new FileOutputStream(files[0].getName().replace(".part1",""));
		for(File f:files) {
			totalSize+=f.length();
		}
		for(int i=0; i<files.length; i++) {
			InputStream ip=new FileInputStream(files[i]);
			lblStatusMerge.setText("Writing "+(i+1)+"/"+files.length+" part...");
			int bytesRead = 0;
            byte[] buffer = new byte[(int)files[i].length()];
            if((bytesRead=ip.read(buffer))>0) {
            	os.write(buffer,0,bytesRead);
            	updateMergeProgress(bytesRead,(int)totalSize);
            }
            ip.close();
            files[i].delete();
		}
		lblStatusMerge.setText("Completed!");
		textFieldMerge.setText("");
		resetMergeProgress();
		os.close();
	}
	
	// update merge file progressbar
	private void updateMergeProgress(int progress, int totalSize) {
		// TODO Auto-generated method stub
		sizeCopied=sizeCopied+progress;
		double per=((double)sizeCopied/totalSize*100);
        percentage =(int)Math.floor(per);
        mergePB.setValue(percentage);
        mergePB.setString(""+ percentage + " %");
	}
	
	//reset merge file progressbar
	private void resetMergeProgress() {
		// TODO Auto-generated method stub
		sizeCopied=0;
        mergePB.setValue(0);
        mergePB.setString(""+ 0 + " %");
	}

	//split file function
	private void SplitFile(int nNoofFiles) throws Exception {
		InputStream ip=new FileInputStream(file);
		int SizeofEachFile = (int) Math.ceil(file.length()/nNoofFiles);
		for(int i=1; i<=nNoofFiles; i++) {
			os=new FileOutputStream(file.getName()+".part"+i);
			int bytesRead = 0;
            byte[] buffer = new byte[SizeofEachFile];
            if((bytesRead=ip.read(buffer, 0, SizeofEachFile))>0) {
            	os.write(buffer,0,bytesRead);
            	updateProgress(bytesRead, (int) file.length());
            }
            stateLbl.setText("Writing "+i+"/"+nNoofFiles+" partion.."); 
            os.close();
		}
		file=null;
		resetProgress();
		textField.setText("");
		partition.setSelectedIndex(0);
		stateLbl.setText("Task Completed!");
		ip.close();
	}
	
	//update split file progressbar
	private void updateProgress(int progress,int totalSize) {
		sizeCopied=sizeCopied+progress;
		double per=((double)sizeCopied/totalSize*100);
        percentage =(int)Math.floor(per);
        pb.setValue(percentage);
        pb.setString(""+ percentage + " %");
	}
	
	//reset split file progress 
	private void resetProgress() {
		sizeCopied=0;
        pb.setValue(0);
        pb.setString(""+ 0 + " %");
	}

}
