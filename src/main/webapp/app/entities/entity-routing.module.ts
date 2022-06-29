import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'dados-pessoais',
        data: { pageTitle: 'hunterappApp.userDadosPessoais.home.title' },
        loadChildren: () => import('./user/dados-pessoais/dados-pessoais.module').then(m => m.DadosPessoaisModule),
      },
      {
        path: 'endereco',
        data: { pageTitle: 'hunterappApp.userEndereco.home.title' },
        loadChildren: () => import('./user/endereco/endereco.module').then(m => m.EnderecoModule),
      },
      {
        path: 'mensagem',
        data: { pageTitle: 'hunterappApp.userMensagem.home.title' },
        loadChildren: () => import('./user/mensagem/mensagem.module').then(m => m.MensagemModule),
      },
      {
        path: 'aviso',
        data: { pageTitle: 'hunterappApp.userAviso.home.title' },
        loadChildren: () => import('./user/aviso/aviso.module').then(m => m.AvisoModule),
      },
      {
        path: 'documento',
        data: { pageTitle: 'hunterappApp.userDocumento.home.title' },
        loadChildren: () => import('./user/documento/documento.module').then(m => m.DocumentoModule),
      },
      {
        path: 'foto',
        data: { pageTitle: 'hunterappApp.fotoFoto.home.title' },
        loadChildren: () => import('./foto/foto/foto.module').then(m => m.FotoModule),
      },
      {
        path: 'foto-avatar',
        data: { pageTitle: 'hunterappApp.fotoFotoAvatar.home.title' },
        loadChildren: () => import('./foto/foto-avatar/foto-avatar.module').then(m => m.FotoAvatarModule),
      },
      {
        path: 'foto-icon',
        data: { pageTitle: 'hunterappApp.fotoFotoIcon.home.title' },
        loadChildren: () => import('./foto/foto-icon/foto-icon.module').then(m => m.FotoIconModule),
      },
      {
        path: 'foto-documento',
        data: { pageTitle: 'hunterappApp.fotoFotoDocumento.home.title' },
        loadChildren: () => import('./foto/foto-documento/foto-documento.module').then(m => m.FotoDocumentoModule),
      },
      {
        path: 'tipo-mensagem',
        data: { pageTitle: 'hunterappApp.configTipoMensagem.home.title' },
        loadChildren: () => import('./config/tipo-mensagem/tipo-mensagem.module').then(m => m.TipoMensagemModule),
      },
      {
        path: 'raca',
        data: { pageTitle: 'hunterappApp.configRaca.home.title' },
        loadChildren: () => import('./config/raca/raca.module').then(m => m.RacaModule),
      },
      {
        path: 'religiao',
        data: { pageTitle: 'hunterappApp.configReligiao.home.title' },
        loadChildren: () => import('./config/religiao/religiao.module').then(m => m.ReligiaoModule),
      },
      {
        path: 'estado-civil',
        data: { pageTitle: 'hunterappApp.configEstadoCivil.home.title' },
        loadChildren: () => import('./config/estado-civil/estado-civil.module').then(m => m.EstadoCivilModule),
      },
      {
        path: 'tipo-pessoa',
        data: { pageTitle: 'hunterappApp.configTipoPessoa.home.title' },
        loadChildren: () => import('./config/tipo-pessoa/tipo-pessoa.module').then(m => m.TipoPessoaModule),
      },
      {
        path: 'tipo-documento',
        data: { pageTitle: 'hunterappApp.configTipoDocumento.home.title' },
        loadChildren: () => import('./config/tipo-documento/tipo-documento.module').then(m => m.TipoDocumentoModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
