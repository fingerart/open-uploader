package me.fingerart.idea.ui.window;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.util.Consumer;
import me.fingerart.idea.presenter.MainPresenter;
import me.fingerart.idea.ui.iview.BaseView;
import me.fingerart.idea.utils.ViewUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

/**
 * Created by FingerArt on 16/10/1.
 */
public class MainWindow extends BaseView implements ToolWindowFactory {
    private JPanel mToolWindow;
    private JComboBox mComboBoxModule;
    private JTextField mTextFieldUrl;
    private JTextField mTextFieldPath;
    private JTextArea mTextAreaInfo;
    private JButton mButtonBrowse;
    private JButton mButtonAdd;
    private JButton mButtonDel;
    private JButton mButtonUpload;
    private JTable mTableParmas;
    private JProgressBar mProgressBar;

    private static final String[] EMPTY_ROW_DATA = {};
    private static final String[] DEFAULT_COLUMN_NAMES = {"Key", "Value"};
    private static final String[][] DEFAULT_DATA = {{"code", "1"}, {"changeLog", ""}};
    private String mSelectedFilePath;
    private Project mProject;
    private DefaultTableModel mParamsModel;
    private MainPresenter mPresenter;

    public MainWindow() {
        initView();
        initEvent();
    }

    private void initView() {
        mParamsModel = new DefaultTableModel(DEFAULT_DATA, DEFAULT_COLUMN_NAMES);
        mTableParmas.setModel(mParamsModel);
    }

    private void initEvent() {
        mTextFieldPath.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }//Empty

            @Override
            public void keyPressed(KeyEvent e) {
            }//Empty

            @Override
            public void keyReleased(KeyEvent e) {
                mSelectedFilePath = mTextFieldPath.getText();
            }
        });
        mButtonBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileChooserDescriptor descriptor = new FileChooserDescriptor(true, false, true, true, false, false);
                VirtualFile toSelect = ProjectManager.getInstance().getOpenProjects()[0].getBaseDir();
                FileChooser.chooseFile(descriptor, null, toSelect, new Consumer<VirtualFile>() {
                    @Override
                    public void consume(VirtualFile virtualFile) {
                        if (virtualFile.exists()) {
                            mSelectedFilePath = virtualFile.getPath();
                            mTextFieldPath.setText(mSelectedFilePath);
                        }
                    }
                });
            }
        });
        mButtonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mParamsModel.addRow(EMPTY_ROW_DATA);
            }
        });
        mButtonDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewUtil.delSelectedRows(mTableParmas);
            }
        });
        mButtonUpload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mPresenter.handleUploadFile(mTextFieldUrl.getText(), mSelectedFilePath, mParamsModel);
            }
        });
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        mPresenter = new MainPresenter(this);
        mProject = project;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(mToolWindow, "", false);
        toolWindow.getContentManager().addContent(content);
        onToolWindowFirstOpen();
    }

    /**
     * 当工具窗口被第一次打开时调用
     */
    private void onToolWindowFirstOpen() {
        ModuleManager moduleManager = ModuleManager.getInstance(mProject);

        //initModules
        LinkedList<String> list = new LinkedList<>();
        for (Module module : moduleManager.getSortedModules()) {
            list.add(module.getName());
        }
        String[] strings = list.toArray(new String[]{});
        mComboBoxModule.setModel(new DefaultComboBoxModel<String>(strings));

        //initPath
//        String moduleFilePath = mModules[0].getModuleFilePath();
//        String path = moduleFilePath.substring(0, moduleFilePath.lastIndexOf('/')) + "/build/outputs/apk/";
    }
}
