package fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.telephony.TelephonyManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saobane.mini_project.MainActivity;
import com.example.saobane.mini_project.R;

import static com.example.saobane.mini_project.Utils.getImage;


/**
 * Created by Saobane on 28/11/2016.
 */

public class ProfilFragment extends Fragment {

    View myView;

    String nom,prenom,score,num,ville;
    byte[] imageUri;
    ImageView etImageViewProfil;
    TextView etNom,etPrenom,etScore,etNum,etVille;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView=inflater.inflate(R.layout.activity_profil,container,false);
        nom= MainActivity.nom;
        prenom=MainActivity.prenom;
        score= MainActivity.scoreF;
        num=MainActivity.number;
        imageUri=MainActivity.imageUri;

        ville=MainActivity.address;

        etNom= (TextView) myView.findViewById(R.id.etNom);
        etPrenom=(TextView) myView.findViewById(R.id.etPrenom);
        etScore=(TextView) myView.findViewById(R.id.etScore);
        etNum=(TextView)myView.findViewById(R.id.etNum);
        etImageViewProfil=(ImageView)myView.findViewById(R.id.imageViewProfil);
        etVille=(TextView)myView.findViewById(R.id.etVille);

        Bitmap bt=getImage(imageUri);

        etNom.setText(nom);
        etPrenom.setText(prenom);
        etScore.setText(score);
        etNum.setText(num);
        etImageViewProfil.setImageBitmap(bt);
        etVille.setText(ville);

        return myView;
    }


}
