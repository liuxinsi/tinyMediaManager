/*
 * Copyright 2012 - 2016 Manuel Laggner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tinymediamanager.ui.movies;

import static org.tinymediamanager.core.Constants.MEDIA_INFORMATION;
import static org.tinymediamanager.core.Constants.MEDIA_SOURCE;

import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.tinymediamanager.core.MediaFileType;
import org.tinymediamanager.core.entities.MediaFile;
import org.tinymediamanager.core.entities.MediaFileAudioStream;
import org.tinymediamanager.core.entities.MediaFileSubtitle;
import org.tinymediamanager.ui.UTF8Control;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * @author Manuel Laggner
 * 
 */
public class MovieMediaInformationPanel extends JPanel {
  private static final long           serialVersionUID = 2513029074142934502L;
  /**
   * @wbp.nls.resourceBundle messages
   */
  private static final ResourceBundle BUNDLE           = ResourceBundle.getBundle("messages", new UTF8Control()); //$NON-NLS-1$

  private MovieSelectionModel         movieSelectionModel;
  private JLabel                      lblRuntime;
  private JCheckBox                   chckbxWatched;
  private JPanel                      panelVideoStreamDetails;
  private JLabel                      lblVideoCodec;
  private JLabel                      lblVideoResolution;
  private JLabel                      lblVideoBitrate;
  private JPanel                      panelAudioStreamT;
  private JPanel                      panelAudioStreamDetails;
  private JPanel                      panelSubtitleT;
  private JPanel                      panelSubtitleDetails;
  private JLabel                      lblSourceT;
  private JLabel                      lblSource;

  /**
   * Instantiates a new movie media information panel.
   * 
   * @param model
   *          the model
   */
  public MovieMediaInformationPanel(MovieSelectionModel model) {
    this.movieSelectionModel = model;
    setLayout(new FormLayout(
        new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("25px"),
            FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("25px"),
            FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("25px"),
            FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("100px:grow"), },
        new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.UNRELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
            FormFactory.NARROW_LINE_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.UNRELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
            FormFactory.UNRELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

    JLabel lblRuntimeT = new JLabel(BUNDLE.getString("metatag.runtime")); //$NON-NLS-1$
    add(lblRuntimeT, "2, 2");

    lblRuntime = new JLabel("");
    add(lblRuntime, "6, 2");

    JLabel lblWatchedT = new JLabel(BUNDLE.getString("metatag.watched")); //$NON-NLS-1$
    add(lblWatchedT, "10, 2");

    chckbxWatched = new JCheckBox("");
    chckbxWatched.setEnabled(false);
    add(chckbxWatched, "14, 2");

    JLabel lblVideoT = new JLabel(BUNDLE.getString("metatag.video")); //$NON-NLS-1$
    add(lblVideoT, "2, 4");

    JLabel lblMovie = new JLabel(BUNDLE.getString("metatag.movie")); //$NON-NLS-1$
    add(lblMovie, "6, 4");

    panelVideoStreamDetails = new JPanel();
    panelVideoStreamDetails.setLayout(new GridLayout(1, 4, 0, 25));
    add(panelVideoStreamDetails, "10, 4, 7, 1, fill, top");

    lblVideoCodec = new JLabel("");
    panelVideoStreamDetails.add(lblVideoCodec);

    lblVideoResolution = new JLabel("");
    panelVideoStreamDetails.add(lblVideoResolution);

    lblVideoBitrate = new JLabel("");
    panelVideoStreamDetails.add(lblVideoBitrate);

    // to create the same spacing as in audio
    panelVideoStreamDetails.add(new JLabel(""));

    lblSourceT = new JLabel(BUNDLE.getString("metatag.source")); //$NON-NLS-1$
    add(lblSourceT, "6, 6");

    lblSource = new JLabel("");
    add(lblSource, "10, 6");

    JLabel lblAudioT = new JLabel(BUNDLE.getString("metatag.audio")); //$NON-NLS-1$
    add(lblAudioT, "2, 8, default, top");

    panelAudioStreamT = new JPanel();
    panelAudioStreamT.setLayout(new GridLayout(0, 1));
    add(panelAudioStreamT, "6, 8, left, top");

    panelAudioStreamDetails = new JPanel();
    panelAudioStreamDetails.setLayout(new GridLayout(0, 4));
    add(panelAudioStreamDetails, "10, 8, 7, 1, fill, top");

    JLabel lblSubtitle = new JLabel(BUNDLE.getString("metatag.subtitles")); //$NON-NLS-1$
    add(lblSubtitle, "2, 10, default, top");

    panelSubtitleT = new JPanel();
    panelSubtitleT.setLayout(new GridLayout(0, 1));
    add(panelSubtitleT, "6, 10, left, top");

    panelSubtitleDetails = new JPanel();
    panelSubtitleDetails.setLayout(new GridLayout(0, 1));
    add(panelSubtitleDetails, "10, 10, 5, 1, left, top");

    // install the propertychangelistener
    PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        String property = propertyChangeEvent.getPropertyName();
        Object source = propertyChangeEvent.getSource();
        // react on selection of a movie and change of media files
        if ((source.getClass() == MovieSelectionModel.class && "selectedMovie".equals(property)) || MEDIA_INFORMATION.equals(property)
            || MEDIA_SOURCE.equals(property)) {
          fillVideoStreamDetails();
          buildAudioStreamDetails();
          buildSubtitleStreamDetails();
        }
      }
    };

    movieSelectionModel.addPropertyChangeListener(propertyChangeListener);
    initDataBindings();
  }

  private void fillVideoStreamDetails() {
    List<MediaFile> mediaFiles = movieSelectionModel.getSelectedMovie().getMediaFiles(MediaFileType.VIDEO);
    if (mediaFiles.size() == 0) {
      return;
    }

    int runtime = movieSelectionModel.getSelectedMovie().getRuntimeFromMediaFiles();
    if (runtime == 0) {
      lblRuntime.setText("");
    }
    else {
      long h = TimeUnit.SECONDS.toHours(runtime);
      long m = TimeUnit.SECONDS.toMinutes(runtime - TimeUnit.HOURS.toSeconds(h));
      long s = TimeUnit.SECONDS.toSeconds(runtime - TimeUnit.HOURS.toSeconds(h) - TimeUnit.MINUTES.toSeconds(m));
      if (s > 30) {
        m += 1; // round seconds
      }
      lblRuntime.setText(h + "h " + String.format("%02d", m) + "m");
    }

    MediaFile mediaFile = movieSelectionModel.getSelectedMovie().getBiggestMediaFile(MediaFileType.VIDEO);
    chckbxWatched.setSelected(movieSelectionModel.getSelectedMovie().isWatched());
    if (mediaFile != null) {
      lblVideoCodec.setText(mediaFile.getVideoCodec());
      lblVideoResolution.setText(mediaFile.getVideoResolution());
      lblVideoBitrate.setText(mediaFile.getBiteRateInKbps());
    }
    lblSource.setText(movieSelectionModel.getSelectedMovie().getMediaSource().toString());
  }

  private void buildAudioStreamDetails() {
    panelAudioStreamT.removeAll();
    panelAudioStreamDetails.removeAll();

    List<MediaFile> mediaFiles = movieSelectionModel.getSelectedMovie().getMediaFilesContainingAudioStreams();

    for (MediaFile mediaFile : mediaFiles) {
      for (int i = 0; i < mediaFile.getAudioStreams().size(); i++) {
        MediaFileAudioStream audioStream = mediaFile.getAudioStreams().get(i);

        if (mediaFile.getType() == MediaFileType.VIDEO) {
          panelAudioStreamT.add(new JLabel(BUNDLE.getString("metatag.internal"))); //$NON-NLS-1$
        }
        else {
          panelAudioStreamT.add(new JLabel(BUNDLE.getString("metatag.external"))); //$NON-NLS-1$
        }

        panelAudioStreamDetails.add(new JLabel(audioStream.getCodec()));
        panelAudioStreamDetails.add(new JLabel(audioStream.getChannels()));
        panelAudioStreamDetails.add(new JLabel(audioStream.getBitrateInKbps()));
        panelAudioStreamDetails.add(new JLabel(audioStream.getLanguage()));
      }
    }

    panelAudioStreamDetails.revalidate();
    panelAudioStreamT.revalidate();
  }

  private void buildSubtitleStreamDetails() {
    panelSubtitleT.removeAll();
    panelSubtitleDetails.removeAll();

    HashSet<MediaFileSubtitle> subs = new HashSet<MediaFileSubtitle>(); // no dupes
    for (MediaFile mediaFile : movieSelectionModel.getSelectedMovie().getMediaFilesContainingSubtitles()) {
      for (int i = 0; i < mediaFile.getSubtitles().size(); i++) {
        MediaFileSubtitle subtitle = mediaFile.getSubtitles().get(i);
        if (mediaFile.getType() == MediaFileType.SUBTITLE) {
          panelSubtitleT.add(new JLabel(BUNDLE.getString("metatag.external"))); //$NON-NLS-1$
          panelSubtitleDetails.add(new JLabel(mediaFile.getFilename()));
        }
        else {
          subs.add(subtitle);
        }
      }
    }
    for (MediaFileSubtitle sub : subs) {
      panelSubtitleT.add(new JLabel(BUNDLE.getString("metatag.internal"))); //$NON-NLS-1$
      String info = sub.getLanguage() + (sub.isForced() ? " forced" : "") + " (" + sub.getCodec() + ")";
      panelSubtitleDetails.add(new JLabel(info));
    }

    panelSubtitleDetails.revalidate();
    panelSubtitleT.revalidate();
  }

  protected void initDataBindings() {
    BeanProperty<MovieSelectionModel, Boolean> movieSelectionModelBeanProperty = BeanProperty.create("selectedMovie.watched");
    BeanProperty<JCheckBox, Boolean> jCheckBoxBeanProperty = BeanProperty.create("selected");
    AutoBinding<MovieSelectionModel, Boolean, JCheckBox, Boolean> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ, movieSelectionModel,
        movieSelectionModelBeanProperty, chckbxWatched, jCheckBoxBeanProperty);
    autoBinding.bind();
  }
}
